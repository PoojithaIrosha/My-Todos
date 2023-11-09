package com.poojithairosha.mytodos.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.poojithairosha.mytodos.databinding.ActivityAddTodoBinding;

public class AddTodoActivity extends AppCompatActivity {

    private TodoViewModel todoViewModel;
    private ActivityAddTodoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTodoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);

        binding.backBtn.setOnClickListener(v -> {
            // Close the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            finish();
        });

        binding.btnAddTodo.setOnClickListener(v -> {

            String todoText = binding.etTodo.getText().toString().trim();
            if (todoText.isEmpty()) {
                Toast.makeText(AddTodoActivity.this, "Todo cannot be empty!", Toast.LENGTH_SHORT).show();
            } else {
                todoViewModel.addTodo(new Todo(todoText, false), () -> {
                    Toast.makeText(AddTodoActivity.this, "New todo added", Toast.LENGTH_SHORT).show();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    finish();
                });
            }
        });
    }
}