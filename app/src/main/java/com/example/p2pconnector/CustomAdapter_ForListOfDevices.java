package com.example.p2pconnector;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.p2pconnector.connector.DeviceInfoHolder;

import java.util.List;

public class CustomAdapter_ForListOfDevices extends ArrayAdapter<DeviceInfoHolder> {
    Context context;
    int resource;

    public CustomAdapter_ForListOfDevices(@NonNull Context context, int resource, @NonNull List<DeviceInfoHolder> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, null);
        DeviceInfoHolder deviceInfoHolder = getItem(position);
        TextView txt_device_name = convertView.findViewById(R.id.txt_device_name);
        TextView txt_device_address = convertView.findViewById(R.id.txt_device_address);
        TextView txt_status = convertView.findViewById(R.id.txt_status);
        Button btn_connect = convertView.findViewById(R.id.btn_connect);

        txt_device_name.setText(deviceInfoHolder.getDevice().deviceName);
        txt_device_address.setText(deviceInfoHolder.getDevice().deviceAddress);
        if(deviceInfoHolder.getDevice().status!=0)
        txt_status.setText("Not connected");
        else  txt_status.setText("Connected");
        Log.d("resp", String.valueOf(deviceInfoHolder.getDevice().status));
        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) context;
                WifiP2pConfig config = new WifiP2pConfig();
                WifiP2pDevice wifiP2pDevice = deviceInfoHolder.getDevice();
                config.deviceAddress = wifiP2pDevice.deviceAddress;
                if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(mainActivity, "Location Permissions not granted", Toast.LENGTH_SHORT).show();
                }

                mainActivity.connectedDevicesManager.getManager().connect(mainActivity.connectedDevicesManager.getChannel(), config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        txt_status.setText("Connected");
                        Toast.makeText(context, "Successfully connected", Toast.LENGTH_SHORT).show();
                        mainActivity.connectedDevicesManager.setCurrentlyConnectedDevice(deviceInfoHolder.getDevice());
                    }

                    @Override
                    public void onFailure(int i) {
                        Toast.makeText(context, "Could not connected", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return convertView;
    }
}
