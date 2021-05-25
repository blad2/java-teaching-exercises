package com.ewencluley.javatasks.httprequests;

import com.sun.net.httpserver.HttpExchange;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mockito.Mockito.*;

public class HandlerPostRqstTest {

    //Create the object to test. Added as a filed to create once
    HandlerPostRqst postRqstHandler = new HandlerPostRqst();
    URI url;
    {
        try {
            url = new URI("http://localhost:8000/exaggerate?factor");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenGetRequest_shouldRespondWith405() throws IOException {

        //Create a mock object of the http exchange class to simulate the action
        HttpExchange mockExchange = mock(HttpExchange.class);

        //mock HTTP request methods and body
        when(mockExchange.getRequestMethod()).thenReturn("GET");

        //add the response to the client **** I don't think I have to add this because the object should stop before sending anything****
        ByteArrayOutputStream bodyResponse = new ByteArrayOutputStream();
        when(mockExchange.getResponseBody()).thenReturn(bodyResponse);

        // mocking url parameters
        when(mockExchange.getRequestURI()).thenReturn(url);

        postRqstHandler.handle(mockExchange);

        String expectedResponse = "Method Not allowed";

        verify(mockExchange).sendResponseHeaders(405, expectedResponse.length());

        Assert.assertEquals(expectedResponse, bodyResponse.toString());
    }

    @Test
    public void givenPostRequestWithSingleChar_shouldReturnSingleChar() throws IOException {

        // creating mock of the httpExchange class to manipulate the client's actions
        HttpExchange exchangeConnection = mock(HttpExchange.class);
        when(exchangeConnection.getRequestMethod()).thenReturn("POST");
        String clientRequestBody = "b";
        ByteArrayInputStream clientBodyInputStream = new ByteArrayInputStream(clientRequestBody.getBytes(StandardCharsets.UTF_8));
        when(exchangeConnection.getRequestBody()).thenReturn(clientBodyInputStream);

        // verify the method is returning the expected data
        ByteArrayOutputStream severResponseBody = new ByteArrayOutputStream();
        when(exchangeConnection.getResponseBody()).thenReturn(severResponseBody);

        // mocking url parameters
        when(exchangeConnection.getRequestURI()).thenReturn(url);

        postRqstHandler.handle(exchangeConnection);

        Assert.assertEquals(clientRequestBody, severResponseBody.toString());
    }

    @Test
    public void givenPostRequestWithSingleDigit_shouldReturnSingleDigitX2() throws IOException{

        // creating mock of the httpExchange class to manipulate the client's actions
        HttpExchange exchangeHandler = mock(HttpExchange.class);
        when(exchangeHandler.getRequestMethod()).thenReturn("POST");
        String clientRequestBody = "9";
        ByteArrayInputStream clientBodyInputStream = new ByteArrayInputStream(clientRequestBody.getBytes(StandardCharsets.UTF_8));
        when(exchangeHandler.getRequestBody()).thenReturn(clientBodyInputStream);

        //manipulate server's actions
        ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
        when(exchangeHandler.getResponseBody()).thenReturn(responseBody);

        // mocking url parameters
        when(exchangeHandler.getRequestURI()).thenReturn(url);

        postRqstHandler.handle(exchangeHandler);

        Pattern urlPattern = Pattern.compile("^.*\\?(.+)=(.+)$");
        Matcher matcher = urlPattern.matcher(url.toString());
        if(matcher.matches()) {
            Assert.assertEquals(String.valueOf(9 * Integer.parseInt(matcher.group(2))), responseBody.toString());
        } else {
            // verify response
            Assert.assertEquals("18", responseBody.toString());
        }
    }

    @Test
    public void givenPostRequestNumberTextBetween_shouldReturnDigitX2StringBetween() throws IOException{

        // creating mock of the httpExchange class to manipulate the client's actions
        HttpExchange exchangeHandler = mock(HttpExchange.class);
        when(exchangeHandler.getRequestMethod()).thenReturn("POST");
        String clientRequestBody = "99f3d";
        ByteArrayInputStream clientBodyInputStream = new ByteArrayInputStream(clientRequestBody.getBytes(StandardCharsets.UTF_8));
        when(exchangeHandler.getRequestBody()).thenReturn(clientBodyInputStream);

        //manipulate server's actions
        ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
        when(exchangeHandler.getResponseBody()).thenReturn(responseBody);

        // mocking url parameters
        when(exchangeHandler.getRequestURI()).thenReturn(url);

        postRqstHandler.handle(exchangeHandler);

        Pattern urlPattern = Pattern.compile("^.*\\?(.+)=(.+)$");
        Matcher matcher = urlPattern.matcher(url.toString());
        if(matcher.matches()) {
            Assert.assertEquals(99 * Integer.parseInt(matcher.group(2)) + "f" + 3 * Integer.parseInt(matcher.group(2)) + "d", responseBody.toString());
        } else {
            Assert.assertEquals("198f6d", responseBody.toString());
        }
    }

    @Test
    public void givenPostRequestStartingDigit_shouldReturnDigitX2ThenString() throws IOException{

        // creating mock of the httpExchange class to manipulate the client's actions
        HttpExchange exchangeHandler = mock(HttpExchange.class);
        when(exchangeHandler.getRequestMethod()).thenReturn("POST");
        String clientRequestBody = "25 days ago";
        ByteArrayInputStream clientBodyInputStream = new ByteArrayInputStream(clientRequestBody.getBytes(StandardCharsets.UTF_8));
        when(exchangeHandler.getRequestBody()).thenReturn(clientBodyInputStream);

        //manipulate server's actions
        ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
        when(exchangeHandler.getResponseBody()).thenReturn(responseBody);

        // mocking url parameters
        when(exchangeHandler.getRequestURI()).thenReturn(url);

        postRqstHandler.handle(exchangeHandler);

        Pattern urlPattern = Pattern.compile("^.*\\?(.+)=(.+)$");
        Matcher matcher = urlPattern.matcher(url.toString());
        if(matcher.matches()) {
            Assert.assertEquals(25 * Integer.parseInt(matcher.group(2)) + " days ago", responseBody.toString());
        } else {
            Assert.assertEquals("50 days ago", responseBody.toString());
        }
    }

    @Test
    public void givenPostRequestStartingString_shouldReturnStringThenDigitX2() throws IOException{

        // creating mock of the httpExchange class to manipulate the client's actions
        HttpExchange exchangeHandler = mock(HttpExchange.class);
        when(exchangeHandler.getRequestMethod()).thenReturn("POST");
        String clientRequestBody = "Bladimir is 25 more information times 5";
        ByteArrayInputStream clientBodyInputStream = new ByteArrayInputStream(clientRequestBody.getBytes(StandardCharsets.UTF_8));
        when(exchangeHandler.getRequestBody()).thenReturn(clientBodyInputStream);

        //manipulate server's actions
        ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
        when(exchangeHandler.getResponseBody()).thenReturn(responseBody);

        // mocking url parameters
        when(exchangeHandler.getRequestURI()).thenReturn(url);

        postRqstHandler.handle(exchangeHandler);

        Pattern urlPattern = Pattern.compile("^.*\\?(.+)=(.+)$");
        Matcher matcher = urlPattern.matcher(url.toString());
        if(matcher.matches()) {
            Assert.assertEquals("Bladimir is " + 25 * Integer.parseInt(matcher.group(2)) + " more information times " +
                    5 * Integer.parseInt(matcher.group(2)), responseBody.toString());
        } else {
            Assert.assertEquals("Bladimir is 50 more information times 10",responseBody.toString());
        }
    }

    @Test
    public void givenPostRequestWithEmptyString_shouldReturnEmptyString() throws IOException{

        // creating mock of the httpExchange class to manipulate the client's actions
        HttpExchange exchangeHandler = mock(HttpExchange.class);
        when(exchangeHandler.getRequestMethod()).thenReturn("POST");
        String clientRequestBody = "";
        ByteArrayInputStream clientBodyInputStream = new ByteArrayInputStream(clientRequestBody.getBytes(StandardCharsets.UTF_8));
        when(exchangeHandler.getRequestBody()).thenReturn(clientBodyInputStream);

        //manipulate server's actions
        ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
        when(exchangeHandler.getResponseBody()).thenReturn(responseBody);

        // mocking url parameters
        when(exchangeHandler.getRequestURI()).thenReturn(url);

        postRqstHandler.handle(exchangeHandler);

        Assert.assertEquals("",responseBody.toString());
    }

    @Test
    public void givenPostRequestStartingStringWithMultipleLinesNewLinesString_shouldReturnStartingStringWithMultipleLinesNewLineString() throws IOException{

        // creating mock of the httpExchange class to manipulate the client's actions
        HttpExchange exchangeHandler = mock(HttpExchange.class);
        when(exchangeHandler.getRequestMethod()).thenReturn("POST");
        String clientRequestBody = "Edward makes $90000 a year\n" +
                "4blad3    \n" +
                "b";
        ByteArrayInputStream clientBodyInputStream = new ByteArrayInputStream(clientRequestBody.getBytes(StandardCharsets.UTF_8));
        when(exchangeHandler.getRequestBody()).thenReturn(clientBodyInputStream);

        //manipulate server's actions
        ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
        when(exchangeHandler.getResponseBody()).thenReturn(responseBody);

        // mocking url parameters
        when(exchangeHandler.getRequestURI()).thenReturn(url);

        postRqstHandler.handle(exchangeHandler);

        Pattern urlPattern = Pattern.compile("^.*\\?(.+)=(.+)$");
        Matcher matcher = urlPattern.matcher(url.toString());
        if(matcher.matches()) {
            Assert.assertEquals("Edward makes $" + 90000 * Integer.parseInt(matcher.group(2)) + " a year\n" +
                    4 * Integer.parseInt(matcher.group(2)) + "blad" + 3 * Integer.parseInt(matcher.group(2)) + "    \n" +
                    "b", responseBody.toString());
        } else {
            Assert.assertEquals("Edward makes $" + 90000 * 2 + " a year\n" +
                    4 * 2 + "blad" + 3 * 2 +"    \n" +
                    "b", responseBody.toString());
        }
    }
}