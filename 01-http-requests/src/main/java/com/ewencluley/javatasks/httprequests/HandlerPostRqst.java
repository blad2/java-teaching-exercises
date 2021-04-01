package com.ewencluley.javatasks.httprequests;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HandlerPostRqst implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // Getting all the exchanged bytes and passing it to a string on the following line
        byte[] exchangedData = exchange.getRequestBody().readAllBytes();
        String[] exchangedDataToStringLines = new String(exchangedData, StandardCharsets.UTF_8).split("\n");

        StringBuilder exchangedDataToString = new StringBuilder();

        // get the numbers from each line,
        // multiply the resulting number by 2
        for (String s : exchangedDataToStringLines) {
            String number = s.replaceAll("\\s*\\D+\\s*","");
            if (!number.isEmpty()) {
                exchangedDataToString.append(s.replace(number, String.valueOf(Integer.parseInt(number) * 2))).append("\n");
            } else {
                exchangedDataToString.append(s).append("\n");
            }
        }
        // filter out non post methods
        if (exchange.getRequestMethod().equals("POST")) {
        responseData(exchange, exchangedDataToString.toString(), 200);
        } else {
            responseData(exchange, "Method Not allowed", 405);
        }
    }

    public void responseData(HttpExchange exchange, String responseMessage, int responseCode) throws IOException{

        // send response and size of message to the client
        byte[] responseMessageBytes = responseMessage.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(responseCode, responseMessage.getBytes(StandardCharsets.UTF_8).length);

        //Get the body to send the message, write the message to on the body and close connection/request
        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(responseMessageBytes);
        responseBody.close();
    }
}
