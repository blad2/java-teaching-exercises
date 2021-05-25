package com.ewencluley.javatasks.httprequests;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HandlerPostRqst implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // Request method is not equal Post return
        if (!exchange.getRequestMethod().equals("POST")) {
            responseData(exchange, "Method Not allowed", 405);
            return;
        }

        // Getting all the exchanged bytes and passing it to a string on the following line
        byte[] exchangedData = exchange.getRequestBody().readAllBytes();
        // ******* the split method returns an empty value when the first chars don't match the regex ******
        String clientBody = new String(exchangedData, StandardCharsets.UTF_8);
        String[] everythingElse = new String(exchangedData, StandardCharsets.UTF_8).split("\\d+");

        StringBuilder responseBodyString = new StringBuilder();

        String factor = getValue(exchange, "factor");
        if (factor.equals("s")) {
            responseData(exchange, "Invalid factor, expected a number",400);
            return;
        } else if (factor.equals("n")) {
            responseData(exchange, "Invalid factor, expected a non empty",400);
            return;
        }

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(clientBody);
        int count = 1;
        boolean index0IsNumber = false;
        while (matcher.find() || count < everythingElse.length) {
            String number = matcher.group();
            if (clientBody.indexOf(number) == 0 || index0IsNumber) {
                if (everythingElse.length > 0) {
                    // when split method is used and the first char in exchanged data is number,
                    // the first item in the array is empty ("")
                    responseBodyString.append(Integer.parseInt(number) * Integer.parseInt(factor) ).append(everythingElse[count]);
                } else {
                    responseBodyString.append(Integer.parseInt(number) * Integer.parseInt(factor));
                }
                count++;
                index0IsNumber = true;
            } else {
                if (everythingElse.length >= 1 ) {
                    responseBodyString.append(everythingElse[count - 1]).append(Integer.parseInt(number) * Integer.parseInt(factor));
                }
                count++;
            }
        }
        // adding the last item from the array when the string starts with a letter,
        // which makes the count to exceed the length of everythingElse array.
        if (count == everythingElse.length && !index0IsNumber) {
            responseBodyString.append(everythingElse[count - 1]);
        }
        // appending a single string if matcher doesn't find a number
        if (responseBodyString.isEmpty()){
            responseBodyString.append(everythingElse[0]);
        }
        responseData(exchange, responseBodyString.toString(), 200);
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

    public static HashMap<String, String> getParametersOrValue(HttpExchange exchangeParameter) {
        HashMap<String, String> parameterValue = new HashMap<>();
        URI url = exchangeParameter.getRequestURI().getQuery() == null ? URI.create("") : exchangeParameter.getRequestURI();
        String urlParamValues = url.toString();
        if (urlParamValues.matches("\\w+=-?\\w+")) {
            for (String param : urlParamValues.split("&")) {
                String[] pair = param.split("=");
                parameterValue.put(pair[0], pair[1]);
            }
        } else {
            return parameterValue;
        }
        return parameterValue;
    }

    public static String getValue (HttpExchange exchange, String keyToFind) {

        String value = getParametersOrValue(exchange).get(keyToFind);

        if (value == null) {
            value = "2";
        }
        if (value.matches("^-?\\d+$")) { // return number is if it is a valid number (negative allowed)
            value = value;
        }
        if (value.matches("\\D+")) { //find any non digit character and return -1
            value =  "s";
        }
        if (value.isBlank()) { // if the key is blank, this will be an "invalid factor, expected non empty
            value = "n";
        }
        return value;
    }
}