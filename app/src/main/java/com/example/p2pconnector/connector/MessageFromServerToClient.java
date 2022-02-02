package com.example.p2pconnector.connector;

public class MessageFromServerToClient {
    String message;

    public MessageFromServerToClient(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
