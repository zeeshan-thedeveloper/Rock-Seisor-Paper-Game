package com.example.p2pconnector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;

import com.example.p2pconnector.connector.Client;
import com.example.p2pconnector.connector.ConnectedDevicesManager;
import com.example.p2pconnector.connector.IpManager;
import com.example.p2pconnector.connector.Server;

public class MainActivity extends AppCompatActivity {
    IntentFilter intentFilter;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    WifiP2pManager manager;
    WifiP2pManager.Channel channel;
    BroadcastReceiver receiver;


    public static ConnectedDevicesManager connectedDevicesManager;
    public static IpManager ipManager;
    public static Server  server;
    public static Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        receiver = new NetworkBroadCastListner(manager, channel, this);



        fragmentManager=getSupportFragmentManager();

        setAskForBeingHostOrClient();
        initializeConnectedDeviceManager();
        initializeIpManager();
        initializeServer();
        initializeClient();
    }

    public void initializeConnectedDeviceManager(){
        connectedDevicesManager = new ConnectedDevicesManager(manager,channel,receiver,this);
    }

    public void initializeIpManager(){
        ipManager = new IpManager(this);
    }
    public void initializeServer(){
        server = new Server();
    }
    public void initializeClient(){client=new Client();}

    public void setListOfAvailableDevices(){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder,new ListOfAvaibleDevices_Fragment(this));
        fragmentTransaction.commit();
    }

    public void setAskForBeingHostOrClient(){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder,new AskForBeingHostOrClient_Fragment(this));
        fragmentTransaction.commit();
    }

    public void setJoinTheGame(){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder,new JoinTheGame_Fragment(this));
        fragmentTransaction.commit();
    }

    public void setStartServerAndWaitForClient(){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder,new StartServerAndWaitForClient_Fragment(this));
        fragmentTransaction.commit();
    }

    public void setClientPlayArea(){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder,new ClientPlayArea(this));
        fragmentTransaction.commit();
    }
    public void setHostPlayArea(){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_holder,new HostPlayArea(this));
        fragmentTransaction.commit();
    }


}