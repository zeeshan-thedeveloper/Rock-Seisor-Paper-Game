package com.example.p2pconnector.connector;

import android.widget.Toast;

import com.example.p2pconnector.MainActivity;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class IpManager {
    MainActivity mainActivity;

    public IpManager(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public List<String> getAllAvailableIps() {
        List<String> list = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
//                        ip += "SiteLocalAddress: "
//                                + inetAddress.getHostAddress() + "\n";
                        list.add(inetAddress.getHostAddress());
                    }
                }
            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(mainActivity, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return list;
    }
}
