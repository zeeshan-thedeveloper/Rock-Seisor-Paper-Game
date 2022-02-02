package com.example.p2pconnector;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.p2pconnector.connector.MessageFromServerToClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;


public class HostPlayArea extends Fragment {


    public HostPlayArea(MainActivity mainActivity) {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_host_play_area, container, false);
        ImageView img_as_opponent = view.findViewById(R.id.img_client_as_opponent);
        ImageView img_self = view.findViewById(R.id.img_host_self);
        Button btn_roll = view.findViewById(R.id.btn_roll_host);
        Random random = new Random();
        //0:paper
        //1:rock
        //2:seisor

        //We will start a thread  here in host which will listen to the inputs coming from client.
        Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String timeStamp="",timeStampTemp="1";

                while (true){
                    Log.d("resp_at_server",""+ MainActivity.server.getInputStreamManager().getResponseReceivedAtServer());
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(MainActivity.server.getInputStreamManager().getResponseReceivedAtServer());
                        timeStamp = obj.getJSONObject("Input").getString("TimeStamp");
                        if(timeStampTemp!=timeStamp) {
                            //Now we can do further.
                            int choice_value_of_host = Integer.parseInt(obj.getJSONObject("Input").getString("Choice"));

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    switch (choice_value_of_host){
                                        case 0:
                                            img_as_opponent.setImageResource(R.drawable.paper);
                                            break;
                                        case 1:
                                            img_as_opponent.setImageResource(R.drawable.rock);
                                            break;
                                        case 2:
                                            img_as_opponent.setImageResource(R.drawable.seisor);
                                            break;
                                    }
                                }
                            });
                            timeStampTemp=timeStamp;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        if(!thread.isAlive())thread.start();

        btn_roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = random.nextInt(3);
                String msg;
                Long tsLong;
                String ts;
                JSONObject json,item;

                switch (value){
                    case 0:
                         json = new JSONObject();
                         item = new JSONObject();
                         msg=null;
                         tsLong = System.currentTimeMillis()/1000;
                         ts = tsLong.toString();
                        try {
                            item.put("From", "HOST");
                            item.put("Choice", "0");
                            item.put("TimeStamp", ts);

                            json.put("Input",item);
                            msg = json.toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(msg!=null){
                            MainActivity.server.getOutPutStreamManager().WriteFromServerToClient(new MessageFromServerToClient(msg));
                            img_self.setImageResource(R.drawable.paper);
                        }

                        break;
                    case 1:
                         json = new JSONObject();
                         item = new JSONObject();
                         msg=null;
                         tsLong = System.currentTimeMillis()/1000;
                         ts = tsLong.toString();
                        try {
                            item.put("From", "HOST");
                            item.put("Choice", "1");
                            item.put("TimeStamp", ts);

                            json.put("Input",item);
                            msg = json.toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(msg!=null){
                            MainActivity.server.getOutPutStreamManager().WriteFromServerToClient(new MessageFromServerToClient(msg));
                            img_self.setImageResource(R.drawable.rock);
                        }

                        break;
                    case 2:
                         json = new JSONObject();
                         item = new JSONObject();
                         msg=null;
                         tsLong = System.currentTimeMillis()/1000;
                         ts = tsLong.toString();
                        try {
                            item.put("From", "HOST");
                            item.put("Choice", "2");
                            item.put("TimeStamp", ts);

                            json.put("Input",item);
                            msg = json.toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(msg!=null){
                            MainActivity.server.getOutPutStreamManager().WriteFromServerToClient(new MessageFromServerToClient(msg));
                            img_self.setImageResource(R.drawable.seisor);
                        }
                        break;
                }
            }
        });
        return view;
    }
}