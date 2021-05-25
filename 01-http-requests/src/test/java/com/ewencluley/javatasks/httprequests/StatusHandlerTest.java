package com.ewencluley.javatasks.httprequests;

import com.sun.net.httpserver.HttpExchange;
import org.junit.Test;
import org.mockito.AdditionalMatchers;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;

public class StatusHandlerTest {
    private StatusHandler handler = new StatusHandler();

    @Test
    public void givenAnyHttpRequest_shouldRespondWithOK() throws IOException {
        // !!!!!!!!! SETUP !!!!!!!!!!!!!
        // create a mock output stream
        OutputStream mockOutputStream = mock(OutputStream.class);

        // create a mock HttpExchange
        HttpExchange mockHttpExchange = mock(HttpExchange.class);
        // that will return the mock output stream when its getResponseBody method is called
        when(mockHttpExchange.getResponseBody()).thenReturn(mockOutputStream);

        // define the expected reponse body bytes
        byte[] expectedResponseBody = "OK".getBytes(StandardCharsets.UTF_8);


        // !!!!!!!!! TEST !!!!!!!!!!
        //run the method we want to test
        handler.handle(mockHttpExchange);

        // !!!!!!!!! VERIFY !!!!!!!!!
        // check that at some point during the method running the mock output stream has its
        // write method called with a byte array with the same bytes as the expected response
        verify(mockOutputStream).write(AdditionalMatchers.aryEq(expectedResponseBody));
        //check that the mock outpout stream's close method was called
        verify(mockOutputStream).close();
        //check that the mock http exchange had its sendResponseHeaders method called with the
        // expected status code and the correct response body length.
        verify(mockHttpExchange).sendResponseHeaders(200, expectedResponseBody.length);
    }

}