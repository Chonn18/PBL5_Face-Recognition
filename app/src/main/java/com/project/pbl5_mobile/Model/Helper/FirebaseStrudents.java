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
import com.project.pbl5_mobile.Model.Entity.Student;

import java.util.ArrayList;
import java.util.List;

public class FirebaseStrudents {
    private static FirebaseStrudents instance;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    private FirebaseStrudents(){
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
    }
    public static synchronized FirebaseStrudents getInstance() {
        if (instance == null) {
            instance = new FirebaseStrudents();
        }
        return instance;
    }


    public Task<List<Student>> getStudentByClassid(String path, int classid) {
        TaskCompletionSource<List<Student>> tcs = new TaskCompletionSource<>();
        ArrayList<Student> students = new ArrayList<>();
        mDatabase.getReference(path).orderByChild("idclass").equalTo(classid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Student s = snapshot1.getValue(Student.class);
                        students.add(s);

                }
                tcs.setResult(students);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tcs.setException(error.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<List<Student>> getStudentByIDClass(String idclass){
        TaskCompletionSource<List<Student>> tcs = new TaskCompletionSource<>();
        ArrayList<Student> students = new ArrayList<>();
        mDatabase.getReference("Student").child("idclass").equalTo(idclass).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot questionSnapshot : snapshot.getChildren()) {
                    // Extract the topic information from each question
                    Student topic = questionSnapshot.getValue(Student.class);
                    if (!students.contains(topic)) {
                        students.add(topic);
                    }
                }
                tcs.setResult(students);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tcs.setException(error.toException());
            }
        });
        return tcs.getTask();
    }

    public Task<List<Student>> getAllStudent(String path) {
        TaskCompletionSource<List<Student>> tcs = new TaskCompletionSource<>();
        ArrayList<Student> students = new ArrayList<>();
        mDatabase.getReference(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot questionSnapshot : snapshot.getChildren()) {
                    // Extract the topic information from each question
                    Student topic = questionSnapshot.getValue(Student.class);
                    if (!students.contains(topic)) {
                        students.add(topic);
                    }
                }
                tcs.setResult(students);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tcs.setException(error.toException());
            }
        });
        return tcs.getTask();
    }
    public Task<List<Student>> getStudentByID(String id)
    {
        TaskCompletionSource<List<Student>> tcs = new TaskCompletionSource<>();
        ArrayList<Student> students = new ArrayList<>();
        Query query = mDatabase.getReference("Student").orderByChild("idclass").equalTo(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot questionSnapshot : snapshot.getChildren()) {
                        // Extract the topic information from each question
                        Student topic = questionSnapshot.getValue(Student.class);

                        if (!students.contains(topic)) {
                            students.add(topic);
                        }
                    }
                    tcs.setResult(students);

                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tcs.setException(error.toException());
            }
        });
        return tcs.getTask();
    }

    public interface StudentCallback {
        void onUserReceived(Student student);
    }
    }
