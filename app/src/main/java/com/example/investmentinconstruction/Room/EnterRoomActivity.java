package com.example.investmentinconstruction.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.investmentinconstruction.ConnectRealtimeDatabase;
import com.example.investmentinconstruction.LoadingActivity;
import com.example.investmentinconstruction.MainActivity;
import com.example.investmentinconstruction.MainBottomNavigation;
import com.example.investmentinconstruction.R;
import com.example.investmentinconstruction.databinding.ActivityEnterRoomBinding;
import com.google.firebase.auth.FirebaseAuth;

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

        boolean signInRoom =  ConnectRealtimeDatabase.getInstance(this).signInRoom(roomCode, FirebaseAuth.getInstance().getCurrentUser().getUid(), nameDistrict);
        Intent intent = new Intent(EnterRoomActivity.this, LoadingActivity.class);
        intent.putExtra("activity", "MainActivity");
        startActivity(intent);
    }

    private void initSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, masDistrict);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding_enterRoom.spinner.setAdapter(adapter);
    }

    public void goToHome(View view) {
        Intent intent = new Intent(EnterRoomActivity.this, MainBottomNavigation.class);
        startActivity(intent);
    }
}
