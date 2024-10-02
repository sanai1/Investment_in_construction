package com.example.investmentinconstruction.Authorization;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.investmentinconstruction.MainBottomNavigation;
import com.example.investmentinconstruction.R;
import com.example.investmentinconstruction.databinding.ActivityCreateAccountBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountActivity extends AppCompatActivity {

    private ActivityCreateAccountBinding binding_createAccount;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding_createAccount = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding_createAccount.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void goToSignIn(View view) {
        Intent intent = new Intent(CreateAccountActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    public void createAccount(View view) {
        String name = binding_createAccount.editTextTextNameCreateAccount.getText().toString();
        String email = binding_createAccount.editTextTextEmailAddressCreateAccount.getText().toString();
        String password = binding_createAccount.editTextTextPasswordCreateAccount1.getText().toString();
        String password2 = binding_createAccount.editTextTextPasswordCreateAccount2.getText().toString();
        
        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !password2.isEmpty()) {
            if (!password.equals(password2)) {
                Toast.makeText(this, "passwords don't match", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 6) {
                Toast.makeText(this, "password is short", Toast.LENGTH_SHORT).show();
            } else {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(CreateAccountActivity.this, MainBottomNavigation.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(CreateAccountActivity.this, "registration failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        } else {
            Toast.makeText(this, "fill in all the fields", Toast.LENGTH_SHORT).show();
        }
    }
}
