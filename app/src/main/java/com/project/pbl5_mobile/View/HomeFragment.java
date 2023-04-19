package com.project.pbl5_mobile.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.pbl5_mobile.Model.Helper.FirebaseClasses;
import com.project.pbl5_mobile.R;
import com.project.pbl5_mobile.ViewModel.ClassAdapter;
import com.project.pbl5_mobile.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ArrayList<String> classList;
    private ClassAdapter clasAdapter;


    public HomeFragment() {
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_home, null , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        classList = new ArrayList<>();
        binding.rvClass.setHasFixedSize(true);
        binding.rvClass.setLayoutManager(new LinearLayoutManager(getActivity()));

        clasAdapter = new ClassAdapter(classList);
        binding.rvClass.setAdapter(clasAdapter);


        FirebaseClasses.getInstance().getClass("Class").addOnCompleteListener(new OnCompleteListener<List<String>>() {
            @Override
            public void onComplete(@NonNull Task<List<String>> task) {
                if(task.isSuccessful()) {
                    classList.addAll(task.getResult());
                    clasAdapter.notifyDataSetChanged();
                }
                else{
                    Exception ex = task.getException();
                }
            }
        });


    }

}