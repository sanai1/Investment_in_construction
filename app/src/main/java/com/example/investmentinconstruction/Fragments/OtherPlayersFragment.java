package com.example.investmentinconstruction.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.investmentinconstruction.LogicClasses.User;
import com.example.investmentinconstruction.MainActivity;
import com.example.investmentinconstruction.R;

import java.util.ArrayList;
import java.util.Map;

public class OtherPlayersFragment extends Fragment {

    private String[] nameList;

    public OtherPlayersFragment(Map<String, User> userMap) {
        nameList = new String[6];
        int i = 0;
        for (User user : userMap.values()) {
            nameList[i++] = user.getUid();
        }
        for (;i < 6;) {
            nameList[i++] = "";
        }
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
            ((MainActivity) getActivity()).goToMainFragment();
        });

        ArrayList<TextView> arrayList = new ArrayList<>();
        arrayList.add(view.findViewById(R.id.textViewPlayerOne));
        arrayList.add(view.findViewById(R.id.textViewPlayerTwo));
        arrayList.add(view.findViewById(R.id.textViewThree));
        arrayList.add(view.findViewById(R.id.textViewFour));
        arrayList.add(view.findViewById(R.id.textViewFive));
        arrayList.add(view.findViewById(R.id.textViewSix));

        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i).setText(nameList[i]);
        }

        arrayList.get(0).setOnClickListener(v -> {
            ((MainActivity) getActivity()).goToPlayerFragment(arrayList.get(0).getText().toString());
        });
        arrayList.get(1).setOnClickListener(v -> {
            ((MainActivity) getActivity()).goToPlayerFragment(arrayList.get(1).getText().toString());
        });
        arrayList.get(2).setOnClickListener(v -> {
            ((MainActivity) getActivity()).goToPlayerFragment(arrayList.get(2).getText().toString());
        });
        arrayList.get(3).setOnClickListener(v -> {
            ((MainActivity) getActivity()).goToPlayerFragment(arrayList.get(3).getText().toString());
        });
        arrayList.get(4).setOnClickListener(v -> {
            ((MainActivity) getActivity()).goToPlayerFragment(arrayList.get(4).getText().toString());
        });
        arrayList.get(5).setOnClickListener(v -> {
            ((MainActivity) getActivity()).goToPlayerFragment(arrayList.get(5).getText().toString());
        });

        return view;
    }
}