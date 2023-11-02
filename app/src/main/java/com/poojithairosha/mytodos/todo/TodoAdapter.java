package com.poojithairosha.mytodos.todo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.poojithairosha.mytodos.R;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private List<Todo> todoList;
    private TodoViewModel todoViewModel;


    public TodoAdapter(List<Todo> todoList, TodoViewModel todoViewModel) {
        this.todoList = todoList;
        this.todoViewModel = todoViewModel;
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox checkBox;
        private final ImageButton cancelBtn;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            cancelBtn = itemView.findViewById(R.id.cancelBtn);
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public ImageButton getCancelBtn() {
            return cancelBtn;
        }
    }

    @NonNull
    @Override
    public TodoAdapter.TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_layout, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.TodoViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        CheckBox checkBox = holder.getCheckBox();
        checkBox.setChecked(todo.isChecked());
        checkBox.setText(todo.getTodo());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("TAG", "Checked: " + isChecked);
                Log.d("TAG", "Checked: " + buttonView.getText());
                todoViewModel.updateTodo(buttonView.getText().toString(), isChecked);
            }
        });

        holder.cancelBtn.setOnClickListener(v -> {
            todoViewModel.deleteTodo(holder.checkBox.getText().toString());
            this.todoList.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

}
