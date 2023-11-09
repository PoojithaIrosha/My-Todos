package com.poojithairosha.mytodos.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;
import com.poojithairosha.mytodos.R;
import com.poojithairosha.mytodos.auth.AuthViewModel;
import com.poojithairosha.mytodos.auth.LoginActivity;
import com.poojithairosha.mytodos.databinding.ActivityTodoBinding;

public class TodoActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private AuthViewModel authViewModel;
    private ActivityTodoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTodoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        binding.menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if(itemId == R.id.nav_signOut) {
                    authViewModel.signOut(() -> {
                        startActivity(new Intent(TodoActivity.this, LoginActivity.class));
                        finish();
                    });
                }

                return true;
            }
        });

    }
}