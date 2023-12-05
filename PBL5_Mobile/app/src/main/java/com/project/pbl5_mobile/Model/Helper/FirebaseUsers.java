package com.project.pbl5_mobile.Model.Helper;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.pbl5_mobile.Model.Entity.User;
import com.project.pbl5_mobile.Model.Entity.UserCheck;

import java.util.ArrayList;
import java.util.List;

public class FirebaseUsers {
    private static FirebaseUsers instance;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    private FirebaseUsers() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
    }

    public static synchronized FirebaseUsers getInstance() {
        if (instance == null) {
            instance = new FirebaseUsers();
        }
        return instance;
    }

    public Task<List<User>> getAllUsers(String path) {
        TaskCompletionSource<List<User>> tcs = new TaskCompletionSource<>();
        ArrayList<User> clases = new ArrayList<>();
        mDatabase.getReference(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    // Extract the topic information from each question
                    User topic = userSnapshot.getValue(User.class);
                    if (!clases.contains(topic)) {
                        clases.add(topic);
                    }
                }
                tcs.setResult(clases);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tcs.setException(error.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<List<User>> getUsersByID(String path,int id) {
        TaskCompletionSource<List<User>> tcs = new TaskCompletionSource<>();
        ArrayList<User> users = new ArrayList<>();
        mDatabase.getReference(path).orderByChild("id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    // Extract the topic information from each question
                    User topic = userSnapshot.getValue(User.class);
                    if (!users.contains(topic)) {
                        users.add(topic);
                    }
                }
                tcs.setResult(users);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tcs.setException(error.toException());
            }
        });
        return tcs.getTask();
    }


    public Task<List<UserCheck>> getAllUserCheck(String path) {
        TaskCompletionSource<List<UserCheck>> tcs = new TaskCompletionSource<>();
        ArrayList<UserCheck> userChecks = new ArrayList<>();
        mDatabase.getReference(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    // Extract the topic information from each question
                    UserCheck topic = userSnapshot.getValue(UserCheck.class);
                    if (!userChecks.contains(topic)) {
                        userChecks.add(topic);
                    }
                }
                tcs.setResult(userChecks);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tcs.setException(error.toException());
            }
        });
        return tcs.getTask();
    }
}
