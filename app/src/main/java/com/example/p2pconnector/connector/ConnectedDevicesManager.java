package com.example.p2pconnector.connector;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.p2pconnector.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ConnectedDevicesManager {
    WifiP2pManager manager;
    WifiP2pManager.Channel channel;
    BroadcastReceiver receiver;
    MainActivity mainActivity;
    WifiP2pDevice currentlyConnectedDevice;

    public ConnectedDevicesManager(WifiP2pManager manager, WifiP2pManager.Channel channel, BroadcastReceiver receiver, MainActivity mainActivity) {
        this.manager = manager;
        this.channel = channel;
        this.receiver = receiver;
        this.mainActivity = mainActivity;
    }

    public WifiP2pDevice getCurrentlyConnectedDevice() {
        return currentlyConnectedDevice;
    }

    public void setCurrentlyConnectedDevice(WifiP2pDevice currentlyConnectedDevice) {
        this.currentlyConnectedDevice = currentlyConnectedDevice;
    }

    public WifiP2pManager getManager() {
        return manager;
    }

    public void setManager(WifiP2pManager manager) {
        this.manager = manager;
    }

    public WifiP2pManager.Channel getChannel() {
        return channel;
    }

    public void setChannel(WifiP2pManager.Channel channel) {
        this.channel = channel;
    }

    public BroadcastReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(BroadcastReceiver receiver) {
        this.receiver = receiver;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public List<DeviceInfoHolder> getTheListOfAllAvailableDevices(WifiP2pManager.PeerListListener peerListListener) {
        List<DeviceInfoHolder> list_of_devices  = null;
        //Discovering peers.
        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(mainActivity, "Location Permissions not granted", Toast.LENGTH_SHORT).show();
        }
        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {

                if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(mainActivity, "Location Permissions not granted", Toast.LENGTH_SHORT).show();
                }
                manager.requestPeers(channel, peerListListener);
            }

            @Override
            public void onFailure(int i) {
                Toast.makeText(mainActivity, "No device found near by", Toast.LENGTH_SHORT).show();
            }
        });


        return null;
    }
}
