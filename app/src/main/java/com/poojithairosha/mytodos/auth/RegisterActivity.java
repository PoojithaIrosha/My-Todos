package com.poojithairosha.mytodos.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.poojithairosha.mytodos.databinding.ActivityRegisterBinding;
import com.poojithairosha.mytodos.todo.TodoActivity;

public class RegisterActivity extends AppCompatActivity {

    private AuthViewModel authViewModel;
    private ActivityRegisterBinding binding;

    @Override
    protected void onStart() {
        super.onStart();
        if (authViewModel.isLoggedIn()) {
            startActivity(new Intent(RegisterActivity.this, TodoActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        binding.loginLink.setOnClickListener(v -> {
            finish();
        });


        binding.btnRegister.setOnClickListener(v -> {
            String displayName = binding.etName.getText().toString().trim();
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (displayName.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Display name is required", Toast.LENGTH_SHORT).show();
            } else if (email.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
            } else if (password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Password is required", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 6) {
                Toast.makeText(RegisterActivity.this, "Password should contain at least 6 characters", Toast.LENGTH_SHORT).show();
            } else {
                authViewModel.register(email, password, displayName, (status) -> {
                    if(status) {
                        startActivity(new Intent(RegisterActivity.this, TodoActivity.class));
                        finish();
                    }else {
                        Toast.makeText(RegisterActivity.this, "Registration failed! Please try again shortly.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}