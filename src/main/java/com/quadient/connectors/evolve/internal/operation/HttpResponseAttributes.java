package com.quadient.connectors.evolve.internal.operation;

import java.util.Map;

public class HttpResponseAttributes {

    private final int statusCode;
    private final String reasonPhrase;
    private final Map<String, String> headers;

    public HttpResponseAttributes(int statusCode, String reasonPhrase, Map<String, String> headers) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.headers = headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
