package com.ewencluley.javatasks.jsonserialization;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class JsonHandler implements HttpHandler {


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            /* Request method isn't POST Stop it and return responseMessage bellow. */
            if (!exchange.getRequestMethod().equals("POST")) {
                response(exchange, "Method Not allowed", 405);
                return;
            }

            /* Deserialize body response to byte array */
            byte[] requestBodyBytes = exchange.getRequestBody().readAllBytes();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            Book book = objectMapper.readValue(requestBodyBytes, Book.class);
            byte[] bookResponse = objectMapper.writeValueAsBytes(book);

            exchange.sendResponseHeaders(200, bookResponse.length);
            exchange.getResponseBody().write(bookResponse);
            exchange.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void response(HttpExchange exchange, String response, int responseCode) throws IOException{

        // send response and size of message to the client
        byte[] responseMessageBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(responseCode, response.getBytes(StandardCharsets.UTF_8).length);

        //Created the the object to send the message. write the message to the body and close connection/request
        OutputStream responseBody = exchange.getResponseBody();
        responseBody.write(responseMessageBytes);
        responseBody.close();
    }
}