package com.example.investmentinconstruction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.example.investmentinconstruction.Authorization.WelcomeActivity;
import com.example.investmentinconstruction.Fragments.BottomMenu.FriendsFragment;
import com.example.investmentinconstruction.Fragments.BottomMenu.HomeFragment;
import com.example.investmentinconstruction.Fragments.BottomMenu.SettingsFragment;
import com.example.investmentinconstruction.Room.CreateRoomActivity;
import com.example.investmentinconstruction.Room.EnterRoomActivity;
import com.example.investmentinconstruction.databinding.MainLayoutBottomnavigationmenuBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainBottomNavigation extends AppCompatActivity {

    private MainLayoutBottomnavigationmenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MainLayoutBottomnavigationmenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.item_friends) {
            selectedFragment = new FriendsFragment();
        } else if (itemId == R.id.item_home) {
            selectedFragment = new HomeFragment();
        } else if (itemId == R.id.item_settings) {
            selectedFragment = new SettingsFragment();
        }
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        }
        return true;
    };

    public void goToEnterRoom() {
        Intent intent = new Intent(MainBottomNavigation.this, EnterRoomActivity.class);
        startActivity(intent);
    }

    public void goToCreateRoom() {
        Intent intent = new Intent(MainBottomNavigation.this, CreateRoomActivity.class);
        startActivity(intent);
    }

    public void exit() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainBottomNavigation.this, WelcomeActivity.class);
        startActivity(intent);
    }
}