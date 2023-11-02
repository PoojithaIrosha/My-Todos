package com.poojithairosha.mytodos.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.poojithairosha.mytodos.R;
import com.poojithairosha.mytodos.auth.AuthViewModel;
import com.poojithairosha.mytodos.todo.TodoAdapter;
import com.poojithairosha.mytodos.todo.TodoViewModel;

public class TodoActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private TodoViewModel todoViewModel;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        RecyclerView rv1 = findViewById(R.id.recyclerView);
        rv1.setLayoutManager(new LinearLayoutManager(TodoActivity.this));
        todoViewModel.getTodos(arrayList -> {
            rv1.setAdapter(new TodoAdapter(arrayList, todoViewModel));
            rv1.getAdapter().notifyDataSetChanged();
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            startActivity(new Intent(TodoActivity.this, AddTodoActivity.class));
        });

        ImageButton btnSignOut = findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(v -> {
            authViewModel.signOut(() -> {
                startActivity(new Intent(TodoActivity.this, LoginActivity.class));
                finish();
            });
        });
    }
}