package com.example.investmentinconstruction.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.investmentinconstruction.ConnectRealtimeDatabase;
import com.example.investmentinconstruction.LoadingActivity;
import com.example.investmentinconstruction.LogicClasses.Advertisement;
import com.example.investmentinconstruction.LogicClasses.User;
import com.example.investmentinconstruction.MainActivity;
import com.example.investmentinconstruction.MainBottomNavigation;
import com.example.investmentinconstruction.R;
import com.example.investmentinconstruction.databinding.ActivityEnterRoomBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

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
        // TODO: поправить переход на новую activity после окончания загрузки

        String uid = FirebaseAuth.getInstance().getUid();
        User user = new User(uid, nameDistrict, 0, null, new HashMap<>(), new HashMap<>());
        user.setNumberStep(0);
        boolean signInRoom =  ConnectRealtimeDatabase.getInstance(this)
                .signInRoom(roomCode, FirebaseAuth.getInstance().getCurrentUser().getUid(), nameDistrict, user);
        Intent intent = new Intent(EnterRoomActivity.this, LoadingActivity.class);
        if (signInRoom) {
            intent.putExtra("activity", "MainActivity");
        } else {
            intent.putExtra("activity", "EnterRoomActivity");
        }
        intent.putExtra("roomCode", roomCode);
        intent.putExtra(User.class.getSimpleName(), user);
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
