package com.poojithairosha.mytodos.auth;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AuthViewModel extends ViewModel {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private final String TAG = "AuthViewModel";

    public AuthViewModel() {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firestore = FirebaseFirestore.getInstance();
    }

    public void login(String email, String password, AuthCallback authCallback) {

        Log.d(TAG, email + " : " + password);

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(authResultTask -> {
            if (authResultTask.isSuccessful()) {
                Log.d(TAG, "signInWithEmail:success");
                FirebaseUser user = firebaseAuth.getCurrentUser();
                authCallback.callback(true);
            } else {
                Log.d(TAG, "signInWithEmail:failure", authResultTask.getException());
                authCallback.callback(false);
            }
        });
    }

    public boolean isLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    public void signOut(Runnable runnable) {
        firebaseAuth.signOut();
        runnable.run();
    }

    public void getUsername(UsernameCallback callback) {
        if (isLoggedIn()) {

            Log.d(TAG, firebaseAuth.getCurrentUser().getUid());
            firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Log.d(TAG, documentSnapshot.getString("name"));
                    callback.start(documentSnapshot.getString("name"));
                }
            });
        }
    }

    public void register(String email, String password, String name, AuthCallback authCallback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    firestore.collection("users").document(firebaseAuth.getCurrentUser().getUid()).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            authCallback.callback(true);
                        }
                    });
                } else {
                    authCallback.callback(false);
                }
            }
        });
    }

    public interface UsernameCallback {
        void start(String name);
    }

    public interface AuthCallback {
        void callback(boolean status);
    }
}
