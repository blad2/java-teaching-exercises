package com.ewencluley.javatasks.httprequests;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    public Server() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/status", new StatusHandler());
        server.createContext("/exaggerate", new HandlerPostRqst());
        server.start();
    }

    public static void main(String[] args) throws IOException {
        new Server();
    }
}
