package com.poojithairosha.mytodos.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.poojithairosha.mytodos.R;
import com.poojithairosha.mytodos.todo.Todo;
import com.poojithairosha.mytodos.todo.TodoViewModel;

public class AddTodoActivity extends AppCompatActivity {

    private TodoViewModel todoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);

        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> {
            // Close the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            finish();
        });

        Button btnAddTodo = findViewById(R.id.btnAddTodo);
        btnAddTodo.setOnClickListener(v -> {
            EditText etTodo = findViewById(R.id.etTodo);
            String todoText = etTodo.getText().toString().trim();

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