package com.example.investmentinconstruction.Room;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.investmentinconstruction.ConnectRealtimeDatabase;
import com.example.investmentinconstruction.LoadingActivity;
import com.example.investmentinconstruction.LogicClasses.Advertisement;
import com.example.investmentinconstruction.LogicClasses.Room;
import com.example.investmentinconstruction.LogicClasses.User;
import com.example.investmentinconstruction.MainBottomNavigation;
import com.example.investmentinconstruction.databinding.ActivityCreateRoomBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateRoomActivity extends AppCompatActivity {

    private ActivityCreateRoomBinding binding_createRoom;
    private FirebaseAuth firebaseAuth;
    private Integer roomCode;
    private String[] masDistrict = {"Arbat", "Gagarin", "Sokolniki"};
    private Map<String, Integer> mapMonth = new HashMap<>();
    private String[] masMonth = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int i = 0; i < masMonth.length; i++) {
            mapMonth.put(masMonth[i], i);
        }

        binding_createRoom = ActivityCreateRoomBinding.inflate(getLayoutInflater());
        setContentView(binding_createRoom.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        initSpinner();

        roomCode = 10000 + (int) (Math.random() * 90000);
        binding_createRoom.textViewRomeCode.setText(String.valueOf(roomCode));
    }

    public void createRoom(View view) {
        Integer numberPeople = Integer.parseInt(binding_createRoom.editTextNumberPeople.getText().toString());
        if (numberPeople < 1 || numberPeople > 4) {
            Toast.makeText(this, "Check the entered number of people", Toast.LENGTH_SHORT).show();
            return;
        }
        Integer numberModelStep;
        if (binding_createRoom.editTextNumberModelStep.getText().toString().equals("")) {
            Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show();
            return;
        } else {
            numberModelStep = Integer.valueOf(binding_createRoom.editTextNumberModelStep.getText().toString());
        }
        if (numberModelStep < 6) {
            Toast.makeText(this, "Enter a longer period", Toast.LENGTH_SHORT).show();
            return;
        } else if (numberModelStep > 24) {
            Toast.makeText(this, "enter a shorter period", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, User> userMap = new HashMap<>();
        String uid = firebaseAuth.getCurrentUser().getUid().toString();
        String nameDistrict = binding_createRoom.spinnerDistrict.getSelectedItem().toString();
        Integer numberMonth = mapMonth.get(binding_createRoom.spinnerMonth.getSelectedItem().toString());

        if (binding_createRoom.checkBoxGalina.isChecked()) {
            String botUid = "BOT" + UUID.randomUUID().toString();
            userMap.put(botUid, new User(botUid, nameDistrict, 0, new Advertisement(), new HashMap<>(), new HashMap<>(), "GalinaBOT", 35, -1));
            userMap.get(botUid).setNumberStep(1);
        }
        if (binding_createRoom.checkBoxIvan.isChecked()) {
            String botUid = "BOT" + UUID.randomUUID().toString();
            userMap.put(botUid, new User(botUid, nameDistrict, 0, new Advertisement(), new HashMap<>(), new HashMap<>(), "IvanBot", 45, 1));
            userMap.get(botUid).setNumberStep(1);
        }
        if (binding_createRoom.checkBoxEdward.isChecked()) {
            String botUid = "BOT" + UUID.randomUUID().toString();
            userMap.put(botUid, new User(botUid, nameDistrict, 0, new Advertisement(), new HashMap<>(), new HashMap<>(), "EdwardBot", 65, 1));
            userMap.get(botUid).setNumberStep(1);
        }

        User user = new User(uid, nameDistrict, 0, new Advertisement(), new HashMap<>(), new HashMap<>(), "name", 15, 0);
        userMap.put(uid, user);

        Room room = new Room(roomCode, numberPeople, 1, numberModelStep, userMap, numberMonth);

        ConnectRealtimeDatabase.getInstance(this).createRoom(room);

        Intent intent = new Intent(CreateRoomActivity.this, LoadingActivity.class);
        intent.putExtra("activity", "MainActivity");
        intent.putExtra("roomCode", roomCode);
        intent.putExtra(User.class.getSimpleName(), room.getUserMap().get(uid));
        startActivity(intent);
    }

    private void initSpinner() {
        ArrayAdapter<String> adapter_District = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, masDistrict);
        adapter_District.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding_createRoom.spinnerDistrict.setAdapter(adapter_District);

        ArrayAdapter<String> adapter_Month = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, masMonth);
        adapter_Month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding_createRoom.spinnerMonth.setAdapter(adapter_Month);
    }

    public void goToHome(View view) {
        Intent intent = new Intent(CreateRoomActivity.this, MainBottomNavigation.class);
        startActivity(intent);
    }
    
    public void detailSetting(View view) {
        Toast.makeText(this, "It will be possible to set more detailed parameters soon", Toast.LENGTH_SHORT).show();
    }

    public void copyRoomCode(View view) {
        ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("", roomCode.toString());
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "copy " + roomCode.toString(), Toast.LENGTH_SHORT).show();
    }

}