package com.example.investmentinconstruction.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.investmentinconstruction.AdapterState.PlayersAdapter;
import com.example.investmentinconstruction.OtherPlayersActivity;
import com.example.investmentinconstruction.R;

public class PlayerFragment extends Fragment {

    private String name;

    public PlayerFragment(String name) {
        this.name = name;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);

        Button button = view.findViewById(R.id.buttonBackPlayer);
        button.setOnClickListener(v -> {
            ((OtherPlayersActivity) getActivity()).back();
        });

//        RecyclerView recyclerView = view.findViewById(R.id.listConstructionPlayerInfo);
//        recyclerView.setOnClickListener(v -> {
//            ((OtherPlayersActivity) getActivity()).
//        });

        return view;
    }
}