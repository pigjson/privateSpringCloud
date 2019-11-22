package com.test.exception;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FallBack implements FallbackProvider {

    @Override
    public String getRoute() {
        return null;
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return null;
            }
            @Override
            public int getRawStatusCode() throws IOException {
                return 0;
            }
            @Override
            public String getStatusText() throws IOException {
                return cause.getMessage();
            }
            @Override
            public void close() {
            }
            @Override
            public InputStream getBody() throws IOException {
                return null;
            }
            @Override
            public HttpHeaders getHeaders() {
                return null;
            }
        };
    }
}

