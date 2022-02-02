package com.example.p2pconnector.connector;

public class MessageFromClientToServer {
    String message;

    public MessageFromClientToServer(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
