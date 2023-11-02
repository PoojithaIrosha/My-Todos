package com.poojithairosha.mytodos.todo;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class TodoViewModel extends ViewModel {
    private final String TAG = "TodoViewModel";

    private final FirebaseFirestore firestore;
    private final FirebaseAuth firebaseAuth;

    private final CollectionReference todoCollection;

    public TodoViewModel() {
         firestore = FirebaseFirestore.getInstance();
         firebaseAuth = FirebaseAuth.getInstance();
         todoCollection = firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid()).collection("todos");
    }

    public void getTodos(TodoListCallback callback) {

        todoCollection.orderBy("date_added", Query.Direction.DESCENDING).addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w(TAG, "Listen failed.", error);
                return;
            }
            ArrayList<Todo> todoArrayList = new ArrayList<>();
            for (QueryDocumentSnapshot doc : value) {
                if (doc.get("todo") != null) {
                    todoArrayList.add(new Todo(doc.getString("todo"), doc.getBoolean("checked")));
                }
            }
            callback.getTodoList(todoArrayList);
            Log.d(TAG, "Todos: " + todoArrayList);
        });
    }

    public void updateTodo(String todo, boolean isChecked) {
        todoCollection.whereEqualTo("todo", todo).limit(1).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    todoCollection.document(document.getId()).update("checked", isChecked);
                }
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }

    public void deleteTodo(String todo) {
        todoCollection.whereEqualTo("todo", todo).limit(1).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    todoCollection.document(document.getId()).delete();
                }
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });
    }

    public void addTodo(Todo todo, Runnable runnable) {
        todoCollection.add(todo).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                runnable.run();
            }
        });
    }

    public interface TodoListCallback {
        void getTodoList(ArrayList<Todo> arrayList);
    }

}
