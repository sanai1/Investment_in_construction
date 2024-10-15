package com.example.investmentinconstruction.Fragments.MainFragments;

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

    private String[] uidList, nameList;
    private String uid;

    public OtherPlayersFragment(Map<String, User> userMap, String uid) {
        this.uid = uid;
        uidList = new String[7];
        nameList = new String[7];
        int i = 0;
        for (User user : userMap.values()) {
            uidList[i] = user.getUid();
            nameList[i++] = user.getName();
        }
        for (;i < 7;) {
            uidList[i] = "";
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
        arrayList.add(view.findViewById(R.id.textViewSeven));

        for (int i = 0; i < arrayList.size(); i++) {
            if (uidList[i].equals("")) {
                arrayList.get(i).setText(uidList[i]);
            } else if (uidList[i].equals(uid)){
                arrayList.get(i).setText("YOU");
            } else {
                arrayList.get(i).setText(nameList[i]);
            }
        }

        arrayList.get(0).setOnClickListener(v -> {
            ((MainActivity) getActivity()).goToPlayerFragment(uidList[0]);
        });
        arrayList.get(1).setOnClickListener(v -> {
            ((MainActivity) getActivity()).goToPlayerFragment(uidList[1]);
        });
        arrayList.get(2).setOnClickListener(v -> {
            ((MainActivity) getActivity()).goToPlayerFragment(uidList[2]);
        });
        arrayList.get(3).setOnClickListener(v -> {
            ((MainActivity) getActivity()).goToPlayerFragment(uidList[3]);
        });
        arrayList.get(4).setOnClickListener(v -> {
            ((MainActivity) getActivity()).goToPlayerFragment(uidList[4]);
        });
        arrayList.get(5).setOnClickListener(v -> {
            ((MainActivity) getActivity()).goToPlayerFragment(uidList[5]);
        });

        return view;
    }
}