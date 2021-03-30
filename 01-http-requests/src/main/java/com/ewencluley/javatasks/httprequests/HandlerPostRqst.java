package com.ewencluley.javatasks.httprequests;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class HandlerPostRqst implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String request = exchange.getRequestMethod();
        InputStream exchangeRequestBody = exchange.getRequestBody();
        byte[] exchangedData = exchangeRequestBody.readAllBytes();
        String exchangedDateToString = new String(exchangedData, StandardCharsets.UTF_8);


        System.out.println(exchangedDateToString);

    }
}
