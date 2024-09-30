package com.quadient.connectors.evolve.internal.connection;

import com.quadient.connectors.evolve.api.generate.MultipartAttachmentFE;
import com.quadient.connectors.evolve.internal.operation.HttpResponseAttributes;
import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mule.runtime.api.message.Message;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.MediaType;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.api.transformation.TransformationService;
import org.mule.runtime.core.internal.message.DefaultMessageBuilder;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpRequestOptions;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.sdk.api.runtime.operation.Result;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.quadient.connectors.evolve.internal.connection.Connection.HEADER_AUTHORIZATION_KEY;
import static com.quadient.connectors.evolve.internal.connection.Connection.HEADER_CONTENT_TYPE_KEY;
import static com.quadient.connectors.evolve.internal.connection.Connection.HEADER_CONTENT_TYPE_VALUE_JSON;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionTest extends TestCase {

    static final String ENDPOINT = "/api/v1/endpoint";
    static final String API_TOKEN = "apiToken";
    static final String COMPANY_HOSTNAME = "https://example.com";
    static final int CONNECTION_TIMEOUT = 10;
    static final TimeUnit CONNECTION_TIMEOUT_UNIT = TimeUnit.MINUTES;

    @Mock
    private HttpClient httpClient;

    @Mock
    private TransformationService transformationService;

    @Captor
    private ArgumentCaptor<HttpRequest> httpRequestArgumentCaptor;

    @Captor
    private ArgumentCaptor<HttpRequestOptions> httpRequestOptionsArgumentCaptor;

    Connection connection;

    @Override
    @Before
    public void setUp() throws Exception {
        connection = new Connection(httpClient, COMPANY_HOSTNAME, API_TOKEN, transformationService, CONNECTION_TIMEOUT, CONNECTION_TIMEOUT_UNIT);
    }

    @Test
    public void testSendPOSTRequest() throws IOException, TimeoutException {
        when(httpClient.send(any(HttpRequest.class), any(HttpRequestOptions.class))).thenReturn(new TestHttpResponse(200));

        Result<InputStream, HttpResponseAttributes> responseAttributesResult = connection.sendPOSTRequest(ENDPOINT, "body");

        verify(httpClient).send(httpRequestArgumentCaptor.capture(), httpRequestOptionsArgumentCaptor.capture());

        assertEquals(COMPANY_HOSTNAME + ENDPOINT, httpRequestArgumentCaptor.getValue().getUri().toString());
        assertEquals("POST", httpRequestArgumentCaptor.getValue().getMethod());
        assertEquals("Bearer " + API_TOKEN, httpRequestArgumentCaptor.getValue().getHeaderValue(HEADER_AUTHORIZATION_KEY));
        assertEquals(HEADER_CONTENT_TYPE_VALUE_JSON, httpRequestArgumentCaptor.getValue().getHeaderValue(HEADER_CONTENT_TYPE_KEY));
        assertEquals(0, httpRequestArgumentCaptor.getValue().getQueryParams().size());
        assertEquals("body", IOUtils.toString(httpRequestArgumentCaptor.getValue().getEntity().getContent(), UTF_8));
        assertEquals(CONNECTION_TIMEOUT_UNIT.toMillis(CONNECTION_TIMEOUT), httpRequestOptionsArgumentCaptor.getValue().getResponseTimeout());

        assertEquals(MediaType.APPLICATION_JSON, responseAttributesResult.getMediaType().get());
        assertEquals("response", IOUtils.toString(responseAttributesResult.getOutput(), UTF_8));
        responseAttributesResult.getAttributes().ifPresent(attributes -> {
            assertEquals(200, attributes.getStatusCode());
            assertEquals("", attributes.getReasonPhrase());
            assertEquals(Collections.singletonMap("Content-Type", MediaType.APPLICATION_JSON.toRfcString()), attributes.getHeaders());
        });
    }

    @Test
    public void testSendRequest() throws IOException, TimeoutException {
        when(httpClient.send(any(HttpRequest.class), any(HttpRequestOptions.class))).thenReturn(new TestHttpResponse(201));
        Map<String, String> uriParams = new HashMap<String, String>() {{
            put("param1", "A");
            put("param2", "B");
        }};

        Result<InputStream, HttpResponseAttributes> responseAttributesResult = connection.sendRequest(HttpConstants.Method.HEAD, ENDPOINT, "body", uriParams);

        verify(httpClient).send(httpRequestArgumentCaptor.capture(), httpRequestOptionsArgumentCaptor.capture());

        assertEquals(COMPANY_HOSTNAME + ENDPOINT, httpRequestArgumentCaptor.getValue().getUri().toString());
        assertEquals("HEAD", httpRequestArgumentCaptor.getValue().getMethod());
        assertEquals("Bearer " + API_TOKEN, httpRequestArgumentCaptor.getValue().getHeaderValue(HEADER_AUTHORIZATION_KEY));
        assertEquals(HEADER_CONTENT_TYPE_VALUE_JSON, httpRequestArgumentCaptor.getValue().getHeaderValue(HEADER_CONTENT_TYPE_KEY));
        assertEquals(2, httpRequestArgumentCaptor.getValue().getQueryParams().size());
        assertEquals("A", httpRequestArgumentCaptor.getValue().getQueryParams().get("param1"));
        assertEquals("B", httpRequestArgumentCaptor.getValue().getQueryParams().get("param2"));
        assertEquals("body", IOUtils.toString(httpRequestArgumentCaptor.getValue().getEntity().getContent(), UTF_8));
        assertEquals(CONNECTION_TIMEOUT_UNIT.toMillis(CONNECTION_TIMEOUT), httpRequestOptionsArgumentCaptor.getValue().getResponseTimeout());

        assertEquals(MediaType.APPLICATION_JSON, responseAttributesResult.getMediaType().get());
        assertEquals("response", IOUtils.toString(responseAttributesResult.getOutput(), UTF_8));
        responseAttributesResult.getAttributes().ifPresent(attributes -> {
            assertEquals(201, attributes.getStatusCode());
            assertEquals("", attributes.getReasonPhrase());
            assertEquals(Collections.singletonMap("Content-Type", MediaType.APPLICATION_JSON.toRfcString()), attributes.getHeaders());
        });
    }

    @Test
    public void testSendRequestMultiPart() throws IOException, TimeoutException {
        when(transformationService.transform(any(Message.class), any(DataType.class))).thenReturn(new DefaultMessageBuilder().mediaType(MediaType.BINARY).payload(new TypedValue<>("data".getBytes(), DataType.BYTE_ARRAY)).build());
        when(httpClient.send(any(HttpRequest.class), any(HttpRequestOptions.class))).thenReturn(new TestHttpResponse(202));
        MultipartAttachmentFE multipartAttachmentFE1 = new MultipartAttachmentFE();
        multipartAttachmentFE1.name = "name";
        multipartAttachmentFE1.multipartData = new TypedValue<>(new ByteArrayInputStream("multipartData1".getBytes()), DataType.INPUT_STREAM);

        MultipartAttachmentFE multipartAttachmentFE2 = new MultipartAttachmentFE();
        multipartAttachmentFE2.name = "name";
        multipartAttachmentFE2.multipartData = new TypedValue<>(new ByteArrayInputStream("multipartData2".getBytes()), DataType.INPUT_STREAM);

        ArrayList<MultipartAttachmentFE> attachments = new ArrayList<MultipartAttachmentFE>() {{
            add(multipartAttachmentFE1);
            add(multipartAttachmentFE2);
        }};

        Result<InputStream, HttpResponseAttributes> responseAttributesResult = connection.sendRequestMultiPart(HttpConstants.Method.PUT, ENDPOINT, "body", attachments);

        verify(httpClient).send(httpRequestArgumentCaptor.capture(), httpRequestOptionsArgumentCaptor.capture());

        assertEquals(COMPANY_HOSTNAME + ENDPOINT, httpRequestArgumentCaptor.getValue().getUri().toString());
        assertEquals("PUT", httpRequestArgumentCaptor.getValue().getMethod());
        assertEquals("Bearer " + API_TOKEN, httpRequestArgumentCaptor.getValue().getHeaderValue(HEADER_AUTHORIZATION_KEY));
        assertEquals("multipart/form-data", httpRequestArgumentCaptor.getValue().getHeaderValue(HEADER_CONTENT_TYPE_KEY));
        assertEquals(0, httpRequestArgumentCaptor.getValue().getQueryParams().size());
        assertEquals(CONNECTION_TIMEOUT_UNIT.toMillis(CONNECTION_TIMEOUT), httpRequestOptionsArgumentCaptor.getValue().getResponseTimeout());
        assertEquals(MediaType.APPLICATION_JSON, responseAttributesResult.getMediaType().get());
        assertEquals("response", IOUtils.toString(responseAttributesResult.getOutput(), UTF_8));
        responseAttributesResult.getAttributes().ifPresent(attributes -> {
            assertEquals(202, attributes.getStatusCode());
            assertEquals("", attributes.getReasonPhrase());
            assertEquals(Collections.singletonMap("Content-Type", MediaType.APPLICATION_JSON.toRfcString()), attributes.getHeaders());
        });
    }
}