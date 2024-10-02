package com.example.investmentinconstruction;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.investmentinconstruction.Authorization.SignInActivity;
import com.example.investmentinconstruction.Authorization.WelcomeActivity;
import com.example.investmentinconstruction.Room.CreateRoomActivity;
import com.example.investmentinconstruction.databinding.ActivityLoadingBinding;

public class LoadingActivity extends AppCompatActivity {

    private ActivityLoadingBinding binding_loading;
    private Animation animation_loading;
    private String argument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle bundle = getIntent().getExtras();
        argument = bundle.get("activity").toString();
        System.out.println(argument);

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
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = null;
                if (argument.equals("MainActivity")) {
                    System.out.println(true);
                    intent = new Intent(LoadingActivity.this, MainActivity.class);
                }
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}