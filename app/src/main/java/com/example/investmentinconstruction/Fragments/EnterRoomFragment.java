package com.example.investmentinconstruction.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.investmentinconstruction.R;
import com.example.investmentinconstruction.Room.EnterRoomActivity;

public class EnterRoomFragment extends Fragment {

    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_enter_room, container, false);

        EditText editText = view.findViewById(R.id.editTextNumberCheckCode);
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        radioGroup.clearCheck();
        RadioButton radioButtonOne = view.findViewById(R.id.radioButtonArbat);
        RadioButton radioButtonTwo = view.findViewById(R.id.radioButtonGagarin);
        RadioButton radioButtonThree = view.findViewById(R.id.radioButtonSokolniki);

        Button button = (Button) view.findViewById(R.id.buttonEnterRoom);
        button.setOnClickListener(v -> {
            int n = -1;
            if (radioButtonOne.isChecked()) n = 0;
            else if (radioButtonTwo.isChecked()) n = 1;
            else if (radioButtonThree.isChecked()) n = 2;
            if (n != -1) {
                if (!editText.getText().toString().equals("")) {
                    ((EnterRoomActivity) getActivity()).enterRoom(n, Integer.parseInt(editText.getText().toString()));
                }
            }
        });

        ImageView imageView = view.findViewById(R.id.imageViewBackEnterRoom);
        imageView.setOnClickListener(v -> {
            ((EnterRoomActivity) getActivity()).goToHome();
        });

        return view;
    }
}