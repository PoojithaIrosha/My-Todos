package com.poojithairosha.mytodos.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.poojithairosha.mytodos.databinding.ActivityLoginBinding;
import com.poojithairosha.mytodos.todo.TodoActivity;

public class LoginActivity extends AppCompatActivity {

    private AuthViewModel authViewModel;
    private ActivityLoginBinding binding;

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
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        binding.btnLogin.setOnClickListener(v -> {

            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Email and password are required", Toast.LENGTH_SHORT).show();
            } else if(password.length() < 6) {
                Toast.makeText(LoginActivity.this, "Password should contain at least 6 characters", Toast.LENGTH_SHORT).show();
            }else {

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

        binding.registerLink.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

    }
}