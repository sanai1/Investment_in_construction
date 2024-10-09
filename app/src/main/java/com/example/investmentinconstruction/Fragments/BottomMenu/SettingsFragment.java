package com.example.investmentinconstruction.Fragments.BottomMenu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.investmentinconstruction.MainBottomNavigation;
import com.example.investmentinconstruction.R;


public class SettingsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        view.findViewById(R.id.imageViewExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainBottomNavigation) getActivity()).exit();
            }
        });

        return view;
    }
}