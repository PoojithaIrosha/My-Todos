package com.poojithairosha.mytodos.todo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poojithairosha.mytodos.R;
import com.poojithairosha.mytodos.auth.AuthViewModel;
import com.poojithairosha.mytodos.databinding.FragmentTodoMainBinding;

public class TodoMainFragment extends Fragment {

    private TodoViewModel todoViewModel;
    private AuthViewModel authViewModel;
    private FragmentTodoMainBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTodoMainBinding.inflate(inflater, container, false);
        return inflater.inflate(R.layout.fragment_todo_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        todoViewModel = new ViewModelProvider(this).get(TodoViewModel.class);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        authViewModel.getUsername(name -> {
            binding.title1.setText("Welcome, " + name);
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        todoViewModel.getTodos(arrayList -> {
            binding.recyclerView.setAdapter(new TodoAdapter(arrayList, todoViewModel));
            binding.recyclerView.getAdapter().notifyDataSetChanged();
        });

        binding.fab.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AddTodoActivity.class));
        });
    }
}