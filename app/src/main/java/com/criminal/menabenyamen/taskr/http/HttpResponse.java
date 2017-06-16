package com.criminal.menabenyamen.taskr.http;

import java.util.List;
import java.util.Map;

/**
 * Created by menabenyamen on 2017-06-08.
 */

public final class HttpResponse {

    private final int statusCode;
    private final Map<String, List<String>> headers;
    private final byte[] response;

    HttpResponse(int statusCode, Map<String, List<String>> headers, byte[] response) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.response = response;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public String getResponseAsString() {
        return new String(response);
    }
}
