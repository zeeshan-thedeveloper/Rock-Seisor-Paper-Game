package com.example.p2pconnector;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class AskForBeingHostOrClient_Fragment extends Fragment {


    MainActivity mainActivity;
    public AskForBeingHostOrClient_Fragment(MainActivity mainActivity) {
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
        View view = inflater.inflate(R.layout.fragment_ask_for_being_host_or_client_, container, false);
        Button btn_host_game = view.findViewById(R.id.btn_host_the_game);
        Button btn_join_the_game = view.findViewById(R.id.btn_join_the_game);
        btn_host_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setListOfAvailableDevices();
            }
        });
        btn_join_the_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setJoinTheGame();
            }
        });

        return view;
    }
}