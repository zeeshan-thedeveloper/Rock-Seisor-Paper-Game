package com.example.p2pconnector.connector;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class InputStreamManager {

    //This will be used for reading responses from client.
    Socket clientSocket;
    Socket serverSocket;
    public InputStreamManager() {
        clientSocket=null;
        serverSocket =null;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public Socket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public String getResponseReceivedAtServer(){
      return Server.getServerStatus();
    }

    public String getResponseReceivedAtClient(){
        return Client.getClientStatus();
    }
}
