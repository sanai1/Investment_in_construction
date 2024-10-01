package com.example.investmentinconstruction.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.investmentinconstruction.EnterRoomActivity;
import com.example.investmentinconstruction.MainActivity;
import com.example.investmentinconstruction.R;
import com.example.investmentinconstruction.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button buttonEnterRoom = (Button) view.findViewById(R.id.buttonEnterRoomHome);
        buttonEnterRoom.setOnClickListener(v -> {
            ((MainActivity) getActivity()).goToEnterRoom();
        });

        Button buttonCreateRoom = (Button) view.findViewById(R.id.buttonCreateRoomHome);
        buttonCreateRoom.setOnClickListener(v -> {
            ((MainActivity) getActivity()).goToCreateRoom();
        });

        return view;
    }
}