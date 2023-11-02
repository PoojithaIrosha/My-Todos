package com.poojithairosha.mytodos.todo;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Date;

public class Todo {
    private String todo;
    private boolean checked;
    private Timestamp date_added;

    public Todo(String todo, boolean checked) {
        this.todo = todo;
        this.checked = checked;
        this.date_added = new Timestamp(new Date(System.currentTimeMillis()));
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public static ArrayList<Todo> getTodoList() {
        return null;
    }

    public Timestamp getDate_added() {
        return date_added;
    }

    public void setDate_added(Timestamp date_added) {
        this.date_added = date_added;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "todo='" + todo + '\'' +
                ", checked=" + checked +
                ", date_added=" + date_added +
                '}';
    }
}
