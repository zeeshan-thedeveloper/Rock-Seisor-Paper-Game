package com.example.p2pconnector.connector;

import android.util.Log;

import com.example.p2pconnector.MainActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static String SERVER_STATUS=null;

    ServerSocket serverSocket;
    static OutPutStreamManager outPutStreamManager=null;
    static InputStreamManager inputStreamManager=null;

    public Server(){
        setOutPutStreamManager(new OutPutStreamManager());
        setInputStreamManager(new InputStreamManager());
    }
    public void StartServerAndListenForClients(String ip,int port){
        Thread thread = new Thread(new StartConnectionListener(ip,port,serverSocket));
        if (!thread.isAlive())
        thread.start();
    }

    public static OutPutStreamManager getOutPutStreamManager() {
        return outPutStreamManager;
    }

    public static void setOutPutStreamManager(OutPutStreamManager outPutStreamManager) {
        Server.outPutStreamManager = outPutStreamManager;
    }

    public static InputStreamManager getInputStreamManager() {
        return inputStreamManager;
    }

    public static void setInputStreamManager(InputStreamManager inputStreamManager) {
        Server.inputStreamManager = inputStreamManager;
    }
    public static String getServerStatus() {
        return SERVER_STATUS;
    }

    public static void setServerStatus(String serverStatus) {
        SERVER_STATUS = serverStatus;
    }

}
class StartConnectionListener implements Runnable{

    String ip;
    int port;
    ServerSocket serverSocket;
    String line;
    boolean connected;

    public StartConnectionListener(String ip, int port, ServerSocket serverSocket) {
        this.ip = ip;
        this.serverSocket=serverSocket;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            Server.setServerStatus("Initializing the socket");
            serverSocket = new ServerSocket(port);
            Server.setServerStatus("Waiting for client to join ...!!");
            Socket client = serverSocket.accept();
            Server.setServerStatus("A client joined");
            Server.getInputStreamManager().setServerSocket(client);
            Server.getOutPutStreamManager().setServerSocket(client);
            //following method we will use to read response.
            Thread thread_server_reader = new Thread(new ServerReader());
            thread_server_reader.start();
               //TODO:Set the buffered reader and writer for communication
        }catch (Exception e){
            Server.setServerStatus(e.getMessage());
        }
    }
}

class ServerReader implements Runnable{
    @Override
    public void run() {
        while (true){
            try {
                String line=null;
                BufferedReader in = new BufferedReader(new InputStreamReader(MainActivity.server.getInputStreamManager().getServerSocket().getInputStream()));
                if((line=in.readLine())!=null){
                    Server.setServerStatus(line);
                }
            }catch (Exception e){
                Log.d("serv_response",""+e.getMessage());
            }
        }
    }
}
