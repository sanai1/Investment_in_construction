package com.example.investmentinconstruction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.example.investmentinconstruction.Fragments.FriendsFragment;
import com.example.investmentinconstruction.Fragments.HomeFragment;
import com.example.investmentinconstruction.Fragments.SettingsFragment;
import com.example.investmentinconstruction.databinding.MainLayoutBottomnavigationmenuBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("investmentinconstruction");
    }

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

    public native String stringFromJNI();
}