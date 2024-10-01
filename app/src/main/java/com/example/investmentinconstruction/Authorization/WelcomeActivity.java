package com.example.investmentinconstruction.Authorization;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.investmentinconstruction.MainActivity;
import com.example.investmentinconstruction.databinding.ActivityWelcomeBinding;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    private ActivityWelcomeBinding binding_welcome;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding_welcome = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding_welcome.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
    }

    protected void onStart() {
        super.onStart();

        if (firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void getStart(View view) {
        Intent intent = new Intent(WelcomeActivity.this, SignInActivity.class);
        startActivity(intent);
    }

}
