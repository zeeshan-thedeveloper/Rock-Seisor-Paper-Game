package com.example.p2pconnector.connector;

import android.util.Log;

import com.example.p2pconnector.MainActivity;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class OutPutStreamManager {
    //Method with which we can write from client to server.
    Socket clientSocket;
    Socket serverSocket;
    public OutPutStreamManager() {
       clientSocket =null;
       serverSocket=null;
    }

    public  void WriteFromClientToServer(MessageFromClientToServer messageFromClientToServer){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final BufferedWriter[] out  = new BufferedWriter[]{new BufferedWriter(new OutputStreamWriter(MainActivity.client.getOutPutStreamManager().getClientSocket().getOutputStream()))};
                    String outMsg = messageFromClientToServer.getMessage()+ System.getProperty("line.separator");
                    out[0].write(outMsg);
                    out[0].flush();
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }) ;
        if(!thread.isAlive())
            thread.start();
    }
    public static void WriteFromServerToClient(MessageFromServerToClient messageFromServerToClient){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                        final BufferedWriter[] out = new BufferedWriter[]{new BufferedWriter(new OutputStreamWriter(Server.getOutPutStreamManager().getServerSocket().getOutputStream()))};
                        String outMsg = messageFromServerToClient.getMessage() + System.getProperty("line.separator");
                        out[0].write(outMsg);
                        out[0].flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }) ;
        if(!thread.isAlive())
            thread.start();
    }

    public Socket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

}
