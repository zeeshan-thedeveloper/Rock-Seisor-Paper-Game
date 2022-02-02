package com.example.p2pconnector;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.p2pconnector.connector.MessageFromServerToClient;

import java.util.List;


public class StartServerAndWaitForClient_Fragment extends Fragment {

    MainActivity mainActivity;
    Handler handler;
    TextView server_status;
    public StartServerAndWaitForClient_Fragment(MainActivity mainActivity) {
        // Required empty public constructor
        this.mainActivity=mainActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_server_and_wait_for_client_, container, false);
        final String[] selecte_ip_str = {null};
        final int[] port = {0};
        ListView list_of_ips = view.findViewById(R.id.list_of_ips);
        TextView txt_select_ip = view.findViewById(R.id.txt_selected_ip);
        EditText txt_port_number = view.findViewById(R.id.txt_port_number);
        EditText txt_message = view.findViewById(R.id.txt_message_to_send_from_server);
        Button btn_send_message = view.findViewById(R.id.btn_send_message_from_server);
        Button btn_start_play_server = view.findViewById(R.id.bt_start_play_server);
        Button btn_start_listening = view.findViewById(R.id.btn_start_listening);
        server_status = view.findViewById(R.id.txt_server_status);

        handler = new Handler();
        //Get list of all available ips from ip manager.
        List<String> list_of_ips_values = mainActivity.ipManager.getAllAvailableIps();
        if(list_of_ips_values.size()>0){
            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(mainActivity,R.layout.ip_list_item,list_of_ips_values);
            list_of_ips.setAdapter(stringArrayAdapter);
        }

        list_of_ips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txt_select_ip.setText("Selected IP :"+list_of_ips_values.get(position));
                selecte_ip_str[0] = list_of_ips_values.get(position);
            }
        });


        btn_start_listening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                port[0] =Integer.parseInt(txt_port_number.getText().toString());
                mainActivity.server.StartServerAndListenForClients(selecte_ip_str[0],port[0]);
                Thread thread = new Thread(new ListenToServerStatusThread());
                if (!thread.isAlive()){
                    thread.start();
                }
            }
        });
        btn_send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.server.getOutPutStreamManager().WriteFromServerToClient(new MessageFromServerToClient(txt_message.getText().toString()));
            }
        });
        btn_start_play_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setHostPlayArea();
            }
        });

        return view;
    }

    class ListenToServerStatusThread implements Runnable{

        @Override
        public void run() {
            while (true)
            {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                       server_status.setText(MainActivity.server.getInputStreamManager().getResponseReceivedAtServer());
                    }
                });

            }
        }
    }
}