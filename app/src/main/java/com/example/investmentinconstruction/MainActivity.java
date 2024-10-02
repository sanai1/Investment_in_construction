package com.example.investmentinconstruction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.example.investmentinconstruction.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding_main = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding_main.getRoot());

        binding_main.navigationMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() != R.id.player1) {
                    binding_main.drawerLayout.close();
                    Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    public void menuPlayer(View view) {
        binding_main.drawerLayout.openDrawer(GravityCompat.START);
    }
}