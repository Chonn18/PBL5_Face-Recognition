package com.project.pbl5_mobile.Model.Helper;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.pbl5_mobile.Model.Entity.Class;

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

    public Task<List<Class>> getClassByID(String path, String id) {
        TaskCompletionSource<List<Class>> tcs = new TaskCompletionSource<>();
        ArrayList<Class> classes = new ArrayList<>();
        mDatabase.getReference(path).orderByChild("idclass").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot questionSnapshot : snapshot.getChildren()) {
                    Class tmp = questionSnapshot.getValue(Class.class);
                    classes.add(tmp);
                }
                tcs.setResult(classes);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tcs.setException(error.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<List<Class>> getClass(String path) {
        TaskCompletionSource<List<Class>> tcs = new TaskCompletionSource<>();
        ArrayList<Class> clases = new ArrayList<>();
        mDatabase.getReference(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot questionSnapshot : snapshot.getChildren()) {
                    // Extract the topic information from each question
                    Class topic = questionSnapshot.getValue(Class.class);
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

//    public Task<List<String>> getidByName(String path, String name) {
//        TaskCompletionSource<List<String>> tcs = new TaskCompletionSource<>();
//        ArrayList<String> questions = new ArrayList<>();
//        mDatabase.getReference(path).orderByChild("classname").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot questionSnapshot : snapshot.getChildren()) {
//                    String question =String.valueOf(questionSnapshot.child("classid").getValue(Integer.class)) ;
//                    questions.add(question);
//                }
//                tcs.setResult(questions);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                tcs.setException(error.toException());
//            }
//        });
//        return tcs.getTask();
//    }


    public Task<List<String>> getidByName(String path, String name) {
        TaskCompletionSource<List<String>> tcs = new TaskCompletionSource<>();
        ArrayList<String> ids = new ArrayList<>();
        mDatabase.getReference(path).orderByChild("classname").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot questionSnapshot : snapshot.getChildren()) {
                    String id =String.valueOf(questionSnapshot.child("classid").getValue(Integer.class)) ;
                    ids.add(id);
                }

                tcs.setResult(ids);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tcs.setException(error.toException());
            }
        });
        return tcs.getTask();
    }


    public interface ClassCallback {
        void onClassReceived(Class c);
    }
    public void getClassByID(String id, final FirebaseClasses.ClassCallback callback)
    {
        Query query = mDatabase.getReference("Class").child(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Class u = snapshot.getValue(Class.class);
                    callback.onClassReceived(u);
                }
//                callback.onUserReceived(new User());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }





}
