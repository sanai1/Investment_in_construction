package com.example.investmentinconstruction;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.investmentinconstruction.Authorization.SignInActivity;
import com.example.investmentinconstruction.Authorization.WelcomeActivity;
import com.example.investmentinconstruction.LogicClasses.User;
import com.example.investmentinconstruction.Room.CreateRoomActivity;
import com.example.investmentinconstruction.Room.EnterRoomActivity;
import com.example.investmentinconstruction.databinding.ActivityLoadingBinding;

public class LoadingActivity extends AppCompatActivity {

    private ActivityLoadingBinding binding_loading;
    private Animation animation_loading;
    private String argument;
    private String roomCode;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        argument = bundle.get("activity").toString();
        roomCode = bundle.get("roomCode").toString();
        user = (User) bundle.getSerializable(User.class.getSimpleName());

        binding_loading = ActivityLoadingBinding.inflate(getLayoutInflater());
        setContentView(binding_loading.getRoot());

        animation_loading = AnimationUtils.loadAnimation(this, R.anim.loading);
        binding_loading.imageViewLoading.startAnimation(animation_loading);
    }

    @Override
    protected void onStart() {
        super.onStart();

        animation_loading.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = null;
                if (argument.equals("MainActivity")) {
                    intent = new Intent(LoadingActivity.this, MainActivity.class);
                } else if (argument.equals("EnterRoomActivity")) {
                    intent = new Intent(LoadingActivity.this, EnterRoomActivity.class);
                }
                intent.putExtra("roomCode", roomCode);
                intent.putExtra(User.class.getSimpleName(), user);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}