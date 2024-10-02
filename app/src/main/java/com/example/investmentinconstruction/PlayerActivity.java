package com.example.investmentinconstruction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.example.investmentinconstruction.databinding.ActivityPlayerBinding;
import com.google.android.material.navigation.NavigationView;

public class PlayerActivity extends AppCompatActivity {

    private ActivityPlayerBinding binding_player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding_player = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding_player.getRoot());

        binding_player.navigationMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.player1) {
                    Intent intent = new Intent(PlayerActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    public void menuPlayer(View view) {
        binding_player.drawerLayout.openDrawer(GravityCompat.START);
    }
}