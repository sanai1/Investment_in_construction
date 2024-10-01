package com.example.investmentinconstruction.Authorization;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.investmentinconstruction.MainActivity;
import com.example.investmentinconstruction.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding_signIn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding_signIn = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding_signIn.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void goToCreateAccount(View view) {
        Intent intent = new Intent(SignInActivity.this, CreateAccountActivity.class);
        startActivity(intent);
    }

    public void signIn(View view) {
        String email = binding_signIn.editTextTextEmailAddressSignIn.getText().toString();
        String password = binding_signIn.editTextTextPasswordSignIn.getText().toString();

        if (!email.isEmpty() && !password.isEmpty()) {
            if (password.length() >= 6) {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(SignInActivity.this, "login hasn`t been completed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(this, "password is short", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "fill in all the fields", Toast.LENGTH_SHORT).show();
        }
    }

}
