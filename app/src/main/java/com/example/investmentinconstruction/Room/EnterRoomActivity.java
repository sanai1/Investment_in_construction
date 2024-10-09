package com.example.investmentinconstruction.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.investmentinconstruction.ConnectRealtimeDatabase;
import com.example.investmentinconstruction.Fragments.EnterRoomFragment;
import com.example.investmentinconstruction.Fragments.MainFragments.LoadingFragment;
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
    private LoadingFragment loadingFragment;
    private EnterRoomFragment enterRoomFragment;
    private String roomCode;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding_enterRoom = ActivityEnterRoomBinding.inflate(getLayoutInflater());
        setContentView(binding_enterRoom.getRoot());

        loadingFragment = new LoadingFragment();
        enterRoomFragment = new EnterRoomFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.containerEnter, enterRoomFragment).commit();
    }

    public void enterRoom(int n, int roomCode) {
        this.roomCode = String.valueOf(roomCode);
        String uid = FirebaseAuth.getInstance().getUid();
        User user = new User(uid, masDistrict[n], 0, new Advertisement(), new HashMap<>(), new HashMap<>());
        this.user = user;
        ConnectRealtimeDatabase.getInstance(this).signInRoom(this.roomCode, user, this);
        getSupportFragmentManager().beginTransaction().replace(R.id.containerEnter, new LoadingFragment()).commit();
    }

    public void again() {
        getSupportFragmentManager().beginTransaction().replace(R.id.containerEnter, enterRoomFragment).commit();
    }

    public void goToGame() {
        Intent intent = new Intent(EnterRoomActivity.this, MainActivity.class);
        intent.putExtra("roomCode", roomCode);
        intent.putExtra(User.class.getSimpleName(), user);
        startActivity(intent);
    }

    public void goToHome() {
        Intent intent = new Intent(EnterRoomActivity.this, MainBottomNavigation.class);
        startActivity(intent);
    }
}
