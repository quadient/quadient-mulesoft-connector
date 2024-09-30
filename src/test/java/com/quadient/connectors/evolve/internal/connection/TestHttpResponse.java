package com.quadient.connectors.evolve.internal.connection;

import org.mule.runtime.api.metadata.MediaType;
import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.http.api.domain.entity.HttpEntity;
import org.mule.runtime.http.api.domain.entity.InputStreamHttpEntity;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import java.io.ByteArrayInputStream;
import java.util.Collection;
import java.util.Collections;

class TestHttpResponse implements HttpResponse {
    private final int statusCode;
    MultiMap<String, String> headers = new MultiMap<>();

    public TestHttpResponse(int statusCode) {
        this.statusCode = statusCode;
        headers.put("Content-Type", MediaType.APPLICATION_JSON.toRfcString());
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getReasonPhrase() {
        return "";
    }

    @Override
    public HttpEntity getEntity() {
        return new InputStreamHttpEntity(new ByteArrayInputStream("response".getBytes()));
    }

    @Override
    public Collection<String> getHeaderNames() {
        return headers.keySet();
    }

    @Override
    public String getHeaderValue(String headerName) {
        return headers.get(headerName);
    }

    @Override
    public String getHeaderValueIgnoreCase(String headerName) {
        return "";
    }

    @Override
    public Collection<String> getHeaderValues(String headerName) {
        return Collections.singleton(headers.get(headerName));
    }

    @Override
    public Collection<String> getHeaderValuesIgnoreCase(String headerName) {
        return Collections.emptyList();
    }

    @Override
    public MultiMap<String, String> getHeaders() {
        return headers;
    }
}