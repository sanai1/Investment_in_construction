package com.example.investmentinconstruction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.investmentinconstruction.databinding.ActivityEnterRoomBinding;

public class EnterRoomActivity extends AppCompatActivity {

    private ActivityEnterRoomBinding binding_enterRoom;
    private String[] masDistrict = {"Arbat", "Gagarin", "Sokolniki"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding_enterRoom = ActivityEnterRoomBinding.inflate(getLayoutInflater());
        setContentView(binding_enterRoom.getRoot());

        initSpinner();
    }

    public void enterRoom(View view) {
        String nameDistrict = binding_enterRoom.spinner.getSelectedItem().toString();
        String roomCode = binding_enterRoom.editTextNumberCheckCode.getText().toString();
        // TODO: сделать вход в комнату firebase (проверяя code)
    }

    private void initSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, masDistrict);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding_enterRoom.spinner.setAdapter(adapter);
    }

    public void goToHome(View view) {
        Intent intent = new Intent(EnterRoomActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
