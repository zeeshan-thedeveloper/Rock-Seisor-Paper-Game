package com.example.p2pconnector.connector;

import android.net.wifi.p2p.WifiP2pDevice;

public class DeviceInfoHolder {
    WifiP2pDevice device;

    public DeviceInfoHolder(WifiP2pDevice device) {
        this.device = device;
    }

    public WifiP2pDevice getDevice() {
        return device;
    }

    public void setDevice(WifiP2pDevice device) {
        this.device = device;
    }
}
