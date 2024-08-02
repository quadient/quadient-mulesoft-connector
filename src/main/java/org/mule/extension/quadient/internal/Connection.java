package org.mule.extension.quadient.internal;


import org.mule.extension.quadient.internal.errors.exception.*;
import org.mule.extension.quadient.internal.operations.HttpResponseAttributes;
import org.mule.extension.quadient.internal.operations.v6.fe.MultipartAttachmentFE;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.MediaType;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.api.transformation.TransformationService;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.domain.entity.EmptyHttpEntity;
import org.mule.runtime.http.api.domain.entity.InputStreamHttpEntity;
import org.mule.runtime.http.api.domain.entity.multipart.HttpPart;
import org.mule.runtime.http.api.domain.entity.multipart.MultipartHttpEntity;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.request.HttpRequestBuilder;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;
import org.mule.sdk.api.runtime.operation.Result;

import static org.mule.runtime.api.message.Message.of;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mule.runtime.api.metadata.DataType.BYTE_ARRAY;

public final class Connection {
    private final String companyHostname;
    private final String apiToken;
    private final HttpClient client;
    private final TransformationService transformationService;

    public Connection(HttpClient httpClient, String companyHostname, String apiToken, TransformationService transformationService) {
        String hostname = companyHostname.trim();
        this.companyHostname = hostname.endsWith("/") ? hostname.substring(0, hostname.length() - 1) : hostname;
        this.apiToken = apiToken;
        client = httpClient;
        this.transformationService = transformationService;
    }

    public Result<InputStream, HttpResponseAttributes> sendPOSTRequest(String endpoint, String body) {
        return sendRequest(HttpConstants.Method.POST, endpoint, body, null);
    }

    public Result<InputStream, HttpResponseAttributes> sendRequest(HttpConstants.Method method, String endpoint, String body, Map<String, String> uriParams) {
        HttpRequestBuilder requestBuilder = HttpRequest.builder()
                .uri(companyHostname + endpoint)
                .method(method)
                .addHeader("Authorization", "Bearer " + apiToken)
                .addHeader("Content-Type", "application/json");

        if (body != null) {
            requestBuilder.entity(new InputStreamHttpEntity(new ByteArrayInputStream(body.getBytes(UTF_8))));
        } else {
            requestBuilder.entity(new EmptyHttpEntity());
        }
        if (uriParams != null) {
            uriParams.forEach(requestBuilder::addQueryParam);
        }
        HttpResponse response;
        try {
            response = client.send(requestBuilder.build());
        } catch (Exception e) {
            throw new UnknownErrorException(e);
        }

        ErrorHandling(response);
        return createResult(response);
    }

    public Result<InputStream, HttpResponseAttributes> sendRequestMultiPart(HttpConstants.Method method, String endpoint, String body, List<MultipartAttachmentFE> attachments) {
        List<HttpPart> parts = new ArrayList<>();
        byte[] bodyBytes = body.getBytes();
        parts.add(new HttpPart("command", "command.json", bodyBytes, "application/json", bodyBytes.length));
        for (MultipartAttachmentFE attachment : attachments) {
            DataType dataType = attachment.getMultipartData().getDataType();
            InputStream value = attachment.getMultipartData().getValue();
            byte[] byteArray = getPayloadAsBytes(value, transformationService);
            parts.add(new HttpPart(attachment.getName(), attachment.getName(), byteArray, dataType.getMediaType().toRfcString(), byteArray.length));
        }
        HttpRequestBuilder requestBuilder = HttpRequest.builder()
                .uri(companyHostname + endpoint)
                .method(method)
                .addHeader("Authorization", "Bearer " + apiToken)
                .addHeader("Content-Type", "multipart/form-data");

        requestBuilder.entity(new MultipartHttpEntity(parts));
        HttpResponse response;
        try {
            response = client.send(requestBuilder.build());
        } catch (Exception e) {
            throw new UnknownErrorException(e);
        }

        ErrorHandling(response);
        return createResult(response);
    }

    public Result<InputStream, HttpResponseAttributes> sendPOSTRequest(String endpoint, TypedValue<Object> body, Map<String, String> headers) {
        Object payload = body.getValue();
        byte[] payloadAsBytes = getPayloadAsBytes(payload, transformationService);
        InputStreamHttpEntity entity = new InputStreamHttpEntity(new ByteArrayInputStream(payloadAsBytes), payloadAsBytes.length);

        HttpRequestBuilder requestBuilder = HttpRequest.builder()
                .uri(companyHostname + endpoint)
                .method(HttpConstants.Method.POST)
                .addHeader("Authorization", "Bearer " + apiToken)
                .addHeader("Content-Type", "application/json")
                .entity(entity);

        headers.forEach(requestBuilder::addHeader);

        HttpResponse response;
        try {
            response = client.send(requestBuilder.build());
        } catch (Exception e) {
            throw new UnknownErrorException(e);
        }

        ErrorHandling(response);
        return createResult(response);
    }

    private byte[] getPayloadAsBytes(Object payload, TransformationService transformationService) {
        return (byte[]) transformationService.transform(of(payload), BYTE_ARRAY).getPayload().getValue();
    }

    private void ErrorHandling(HttpResponse response) {
        if (response.getStatusCode() == HttpConstants.HttpStatus.OK.getStatusCode()) {
            return;
        }
        try {
            if (response.getStatusCode() == HttpConstants.HttpStatus.UNAUTHORIZED.getStatusCode()) {
                throw new UnauthorizedException(new Exception(new String(response.getEntity().getBytes())));
            }
            if (response.getStatusCode() == HttpConstants.HttpStatus.BAD_REQUEST.getStatusCode()) {
                throw new BadRequestException(new Exception(new String(response.getEntity().getBytes())));
            }
            if (response.getStatusCode() == HttpConstants.HttpStatus.NOT_FOUND.getStatusCode()) {
                throw new NotFoundException(new Exception("Service endpoint not found"));
            }
            if (response.getStatusCode() == HttpConstants.HttpStatus.TOO_MANY_REQUESTS.getStatusCode()) {
                throw new TooManyRequestsException(new Exception(new String(response.getEntity().getBytes())));
            }
            throw new UnknownErrorException(new Exception(new String(response.getEntity().getBytes())));
        } catch (IOException e) {
            throw new UnknownErrorException(e);
        }
    }

    private Result<InputStream, HttpResponseAttributes> createResult(HttpResponse response) {
        MediaType mediaType = response.getHeaderValue("Content-Type") != null ? MediaType.parse(response.getHeaderValue("Content-Type")) : MediaType.ANY;
        return Result.<InputStream, HttpResponseAttributes>builder()
                .output(response.getEntity().getContent())
                .attributes(createAttributes(response))
                .mediaType(mediaType)
                .build();
    }

    private HttpResponseAttributes createAttributes(HttpResponse response) {
        return new HttpResponseAttributes(response.getStatusCode(), response.getReasonPhrase(), response.getHeaders());
    }
}
