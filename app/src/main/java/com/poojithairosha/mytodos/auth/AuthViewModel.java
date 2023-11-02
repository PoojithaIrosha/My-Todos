package com.poojithairosha.mytodos.auth;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends ViewModel {

    private FirebaseAuth firebaseAuth;
    private final String TAG = "AuthViewModel";

    public AuthViewModel() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public void login(String email, String password, LoginCallback loginCallback) {

        Log.d(TAG, email + " : " + password);

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(authResultTask -> {
            if(authResultTask.isSuccessful()) {
                Log.d(TAG, "signInWithEmail:success");
                FirebaseUser user = firebaseAuth.getCurrentUser();
                loginCallback.callback(true);
            }else {
                Log.d(TAG, "signInWithEmail:failure", authResultTask.getException());
                loginCallback.callback(false);
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

    public interface LoginCallback {
        void callback(boolean isLoggedIn);
    }

}
