package com.example.p2pconnector;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.p2pconnector.connector.MessageFromClientToServer;


public class JoinTheGame_Fragment extends Fragment {

    MainActivity mainActivity;
    public JoinTheGame_Fragment(MainActivity mainActivity) {
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
        // Inflate the layout for this fragment

        View  view =  inflater.inflate(R.layout.fragment_join_the_game_, container, false);
        EditText txt_ip_to_connect = view.findViewById(R.id.txt_ip_to_connect);
        EditText txt_port_to_connect = view.findViewById(R.id.txt_port_to_connect);
        Button btn_join = view.findViewById(R.id.btn_join);
        EditText txt_message_sent_from_client = view.findViewById(R.id.txt_message_to_send_from_client);
        Button btn_send_message_from_client = view.findViewById(R.id.btn_send_message_from_client);
        TextView txt_response_from_server = view.findViewById(R.id.txt_response_from_server);
        Button btn_play_client = view.findViewById(R.id.btn_start_play_client);
        //Call client method to join.
        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mainActivity.client.connectToTheServer(txt_ip_to_connect.getText().toString(), Integer.parseInt(txt_port_to_connect.getText().toString()));
                }catch (Exception e){
                    Toast.makeText(mainActivity, "Please provide valid inputs", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_send_message_from_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  MainActivity.client.getOutPutStreamManager().WriteFromClientToServer(new MessageFromClientToServer(txt_message_sent_from_client.getText().toString()));
            }
        });

        //get responses from server.
        Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {

            @SuppressLint("LongLogTag")
            @Override
            public void run() {
                while (true){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            txt_response_from_server.setText(""+MainActivity.client.getInputStreamManager().getResponseReceivedAtClient());
                        }
                    });
                    Log.d("response from server into client",""+MainActivity.client.getInputStreamManager().getResponseReceivedAtClient());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        if(!thread.isAlive()) thread.start();

        btn_play_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setClientPlayArea();
            }
        });

        return view;
    }
}