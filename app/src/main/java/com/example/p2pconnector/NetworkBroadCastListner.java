package com.example.p2pconnector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

public class NetworkBroadCastListner extends BroadcastReceiver {
    WifiP2pManager manager;
    android.net.wifi.p2p.WifiP2pManager.Channel channel;
    MainActivity mainActivity;
    public NetworkBroadCastListner(WifiP2pManager manager, WifiP2pManager.Channel channel, MainActivity mainActivity) {
        this.mainActivity=mainActivity;
        this.manager=manager;
        this.channel=channel;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi P2P is enabled
                Toast.makeText(mainActivity, "", Toast.LENGTH_SHORT).show();
            } else {
                // Wi-Fi P2P is not enabled
                Toast.makeText(mainActivity, "Please allow location permissions", Toast.LENGTH_SHORT).show();
            }
        }
    }
}