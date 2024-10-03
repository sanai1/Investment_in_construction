package com.example.investmentinconstruction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.example.investmentinconstruction.DialogFragment.NewConstruction;
import com.example.investmentinconstruction.LogicClasses.User;
import com.example.investmentinconstruction.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NewConstruction.DialogListenerAdd{

    private ActivityMainBinding binding_main;
    private String roomCode;
    private User user;
    protected NewConstruction newConstruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding_main = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding_main.getRoot());

        Bundle bundle = getIntent().getExtras();
        roomCode = bundle.get("roomCode").toString();
        user = (User) bundle.getSerializable(User.class.getSimpleName());

        binding_main.textViewNameGame.setText(roomCode);
        binding_main.textViewCountMoney.setText(user.getProfitFull().toString());

        newConstruction = new NewConstruction();
        newConstruction.setMyDialogListener(this);

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

    public void addConstruction(View view) {
        newConstruction.show(getSupportFragmentManager(), "newConstruction");
    }

    public void step(View view) {

    }

    @Override
    public void onDialogClickListener() {
        System.out.println("ggggggggggggggggggggggg");
    }
}