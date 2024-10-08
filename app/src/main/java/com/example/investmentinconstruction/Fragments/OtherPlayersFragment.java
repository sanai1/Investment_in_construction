package com.example.investmentinconstruction.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.investmentinconstruction.LogicClasses.User;
import com.example.investmentinconstruction.MainActivity;
import com.example.investmentinconstruction.OtherPlayersActivity;
import com.example.investmentinconstruction.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OtherPlayersFragment extends Fragment {

    private ArrayList<String> nameList;
    private User user;
    private Context mainActivity;

    public OtherPlayersFragment(User user, MainActivity mainActivity, Map<String, User> nameList) {
        this.user = user;
        this.mainActivity = mainActivity;
        this.nameList = nameList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_players, container, false);

        ImageView imageViewBack = view.findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(v -> {
            mainActivity.replaceFragment(user);
        });

        TextView textViewOne = view.findViewById(R.id.textViewPlayerOne);
        textViewOne.setText(nameList.);
        textViewOne.setOnClickListener(v -> {
            mainActivity.goToPlayerFragment(textViewOne.getText().toString());
        });

        TextView textViewTwo = view.findViewById(R.id.textViewPlayerTwo);
        textViewTwo.setOnClickListener(v -> {
            mainActivity.goToPlayerFragment(textViewTwo.getText().toString());
        });

        TextView textViewThree = view.findViewById(R.id.textViewThree);
        textViewThree.setOnClickListener(v -> {
            mainActivity.goToPlayerFragment(textViewThree.getText().toString());
        });

        TextView textViewPlayerFour = view.findViewById(R.id.textViewFour);
        textViewPlayerFour.setOnClickListener(v -> {
            mainActivity.goToPlayerFragment(textViewPlayerFour.getText().toString());
        });

        TextView textViewPlayerFive = view.findViewById(R.id.textViewFive);
        textViewPlayerFive.setOnClickListener(v -> {
            mainActivity.goToPlayerFragment(textViewPlayerFive.getText().toString());
        });

        TextView textViewPlayerSix = view.findViewById(R.id.textViewSix);
        textViewPlayerSix.setOnClickListener(v -> {
            mainActivity.goToPlayerFragment(textViewPlayerSix.getText().toString());
        });

        return view;
    }
}