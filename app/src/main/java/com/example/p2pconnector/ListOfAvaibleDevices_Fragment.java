package com.example.p2pconnector;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.p2pconnector.connector.DeviceInfoHolder;

import java.util.ArrayList;
import java.util.List;


public class ListOfAvaibleDevices_Fragment extends Fragment {

    List<DeviceInfoHolder> listOfDevices;
    MainActivity mainActivity;
    CustomAdapter_ForListOfDevices customAdapter_forListOfDevices;
    public ListOfAvaibleDevices_Fragment(MainActivity mainActivity) {
        // Required empty public constructor
        this.mainActivity=mainActivity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listOfDevices = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_list_of_avaible_devices_, container, false);
        ListView list_view_of_devices = view.findViewById(R.id.list_of_devices);
        Button btn_start_server_and_wait_for_client = view.findViewById(R.id.btn_start_server_and_wait_for_client);
        Button btn_refresh_list = view.findViewById(R.id.btn_refresh_list);
        //Get list of devices from ConnectedDeviceManager;

        WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
                    listOfDevices.clear();
                    for (WifiP2pDevice device : wifiP2pDeviceList.getDeviceList()){
                        listOfDevices.add(new DeviceInfoHolder(device));
                    }
                    //Set to list view
                    Toast.makeText(mainActivity, "Number of devices available :"+listOfDevices.size(), Toast.LENGTH_SHORT).show();
                    customAdapter_forListOfDevices = new CustomAdapter_ForListOfDevices(mainActivity,R.layout.device_list_item,listOfDevices);
                    list_view_of_devices.setAdapter(customAdapter_forListOfDevices);
            }
        };

        mainActivity.connectedDevicesManager.getTheListOfAllAvailableDevices(peerListListener);
        btn_refresh_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.connectedDevicesManager.getTheListOfAllAvailableDevices(peerListListener);
            }
        });

        btn_start_server_and_wait_for_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setStartServerAndWaitForClient();
            }
        });

        return view;
    }
}