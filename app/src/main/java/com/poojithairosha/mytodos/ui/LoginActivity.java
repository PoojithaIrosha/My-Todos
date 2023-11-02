package com.poojithairosha.mytodos.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.poojithairosha.mytodos.R;
import com.poojithairosha.mytodos.auth.AuthViewModel;

public class LoginActivity extends AppCompatActivity {

    private AuthViewModel authViewModel;

    @Override
    protected void onStart() {
        super.onStart();
        if (authViewModel.isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, TodoActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        Button loginBtn = findViewById(R.id.btnLogin);
        loginBtn.setOnClickListener(v -> {

            EditText etEmail = findViewById(R.id.etEmail);
            EditText etPassword = findViewById(R.id.etPassword);

            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Email and password are required", Toast.LENGTH_SHORT).show();
            } else {

                authViewModel.login(email, password, isLoggedIn -> {
                    if (isLoggedIn) {
                        startActivity(new Intent(LoginActivity.this, TodoActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

    }
}