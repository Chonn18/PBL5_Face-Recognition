package com.project.pbl5_mobile.Model.Helper;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseClasses {

    private static FirebaseClasses instance;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    private FirebaseClasses() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
    }

    public static synchronized FirebaseClasses getInstance() {
        if (instance == null) {
            instance = new FirebaseClasses();
        }
        return instance;
    }

    public Task<List<Class>> getQuestionsByCategory(String path, String category) {
        TaskCompletionSource<List<Class>> tcs = new TaskCompletionSource<>();
        ArrayList<Class> questions = new ArrayList<>();
        mDatabase.getReference(path).orderByChild("classname").equalTo(category).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot questionSnapshot : snapshot.getChildren()) {
                    Class question = questionSnapshot.getValue(Class.class);
                    questions.add(question);
                }
                tcs.setResult(questions);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tcs.setException(error.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<List<String>> getClass(String path) {
        TaskCompletionSource<List<String>> tcs = new TaskCompletionSource<>();
        ArrayList<String> clases = new ArrayList<>();
        mDatabase.getReference(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot questionSnapshot : snapshot.getChildren()) {
                    // Extract the topic information from each question
                    String topic = questionSnapshot.child("classname").getValue(String.class);
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



}
