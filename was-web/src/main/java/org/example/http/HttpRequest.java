package org.example.http;

import org.example.http.request.RequestLine;
import org.example.http.request.query.QueryStrings;

import java.io.BufferedReader;
import java.io.IOException;

public class HttpRequest {
    private final RequestLine requestLine;
//    private final HttpHeaders httpHeaders;
//    private final Body body;

    public HttpRequest(BufferedReader br) throws IOException {
        this.requestLine = new RequestLine(br.readLine()); // GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1
    }

    public boolean isGetRequest() {
        return requestLine.isGetRequest();
    }

    public boolean matchPath(String path) {
        return requestLine.matchPath(path);
    }

    public QueryStrings getQueryString() {
        return requestLine.getQueryStrings();
    }
}
