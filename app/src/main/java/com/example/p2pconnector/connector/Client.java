package com.example.p2pconnector.connector;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    static Socket socket;
    static OutPutStreamManager outPutStreamManager;
    static InputStreamManager inputStreamManager;
    public static String CLIENT_STATUS="";
    public Client(){
        outPutStreamManager = new OutPutStreamManager();
        inputStreamManager = new InputStreamManager();
    }

    public static OutPutStreamManager getOutPutStreamManager() {
        return outPutStreamManager;
    }

    public static void setOutPutStreamManager(OutPutStreamManager outPutStreamManager) {
        Client.outPutStreamManager = outPutStreamManager;
    }

    public static InputStreamManager getInputStreamManager() {
        return inputStreamManager;
    }

    public static void setInputStreamManager(InputStreamManager inputStreamManager) {
        Client.inputStreamManager = inputStreamManager;
    }

    public void connectToTheServer(String ip, int port){
        try{

            Log.d("response","Join the game with IP : "+ip+" and port :"+port);
            Client.setClientStatus("Join the game with IP : "+ip+" and port :"+port);
            Thread thread = new Thread(new joinTheGameThread(ip,port));
            if (!thread.isAlive())
                thread.start();
        }catch (Exception e){
         Client.setClientStatus(e.getMessage());
         Log.d("error",""+e.getMessage());
        }
    }

    @SuppressLint("LongLogTag")
    private void runTcpConnection(String serverIpAddress, int SERVERPORT) {
        try {
            setClientStatus("Trying to connect with server");
            socket = new Socket(serverIpAddress, SERVERPORT);
            outPutStreamManager.setClientSocket(socket);
            inputStreamManager.setClientSocket(socket);

            setClientStatus("Connected with clients");
            //Listening to incoming response from server.
            Thread thread = new Thread(new ClientReader());
            if(!thread.isAlive())thread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public static String getClientStatus() {
        return CLIENT_STATUS;
    }

    public static void setClientStatus(String clientStatus) {
        CLIENT_STATUS = clientStatus;
    }

    class joinTheGameThread implements Runnable{
        String ip;
        int port;
        public joinTheGameThread(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }
        @Override
        public void run() {
            runTcpConnection(ip,port);
        }
    }

    class ClientReader implements Runnable{

        @Override
        public void run() {
            while (true){
                try {
                    String line=null;
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    if((line=in.readLine())!=null){
                        Client.setClientStatus(line);
                    }
                }catch (Exception e){
                    Log.d("client_resp",""+e.getMessage());
                }
            }
        }
    }
}
