package com.example.investmentinconstruction.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.investmentinconstruction.AdapterState.PlayerAdapter;
import com.example.investmentinconstruction.LogicClasses.User;
import com.example.investmentinconstruction.MainActivity;
import com.example.investmentinconstruction.R;

public class PlayerFragment extends Fragment {

    private User user;
    private PlayerAdapter playerAdapter;
    private View view;

    public PlayerFragment(PlayerAdapter playerAdapter, User user) {
        this.playerAdapter = playerAdapter;
        this.user = user;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public PlayerAdapter getPlayerAdapter() {
        return playerAdapter;
    }

    public void setPlayerAdapter(PlayerAdapter playerAdapter) {
        this.playerAdapter = playerAdapter;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void initRecycleView() {
        RecyclerView recyclerView = view.findViewById(R.id.listConstructionPlayerInfo);
        recyclerView.setAdapter(playerAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_player, container, false);

        TextView textView = view.findViewById(R.id.textViewNamePlayer);
        textView.setText(user.getUid());

        Button button = view.findViewById(R.id.buttonBackPlayer);
        button.setOnClickListener(v -> {
            ((MainActivity) getActivity()).goToMainFragment();
        });

        RecyclerView recyclerView = view.findViewById(R.id.listConstructionPlayerInfo);
        recyclerView.setAdapter(playerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
}