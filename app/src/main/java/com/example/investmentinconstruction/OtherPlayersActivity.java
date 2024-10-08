package com.example.investmentinconstruction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.example.investmentinconstruction.LogicClasses.User;
import com.example.investmentinconstruction.databinding.ActivityPlayerBinding;
import com.google.android.material.navigation.NavigationView;

public class OtherPlayersActivity extends AppCompatActivity {

    private ActivityPlayerBinding binding_player;
    private String roomCode;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding_player = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding_player.getRoot());

        Bundle bundle = getIntent().getExtras();
        roomCode = bundle.get("roomCode").toString();
        user = (User) bundle.getSerializable(User.class.getSimpleName());

        binding_player.navigationMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.me) {
                    binding_player.drawerLayout.close();
                    Intent intent = new Intent(OtherPlayersActivity.this, MainActivity.class);
                    intent.putExtra("roomCode", roomCode);
                    intent.putExtra(User.class.getSimpleName(), user);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.exit) {
                    binding_player.drawerLayout.close();
                    Intent intent = new Intent(OtherPlayersActivity.this, MainBottomNavigation.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.otherPlayers) {
                    binding_player.drawerLayout.close();
                }
                return true;
            }
        });
    }

    public void menuPlayer(View view) {
        binding_player.drawerLayout.openDrawer(GravityCompat.START);
    }
}