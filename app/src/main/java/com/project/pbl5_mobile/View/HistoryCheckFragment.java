package com.project.pbl5_mobile.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.pbl5_mobile.Model.Entity.User;
import com.project.pbl5_mobile.Model.Entity.UserCheck;
import com.project.pbl5_mobile.Model.Helper.FirebaseUsers;
import com.project.pbl5_mobile.R;
import com.project.pbl5_mobile.ViewModel.CheckAdapter;
import com.project.pbl5_mobile.ViewModel.HistoryAdapter;
import com.project.pbl5_mobile.databinding.FragmentHistoryCheckBinding;

import java.util.ArrayList;
import java.util.List;


public class HistoryCheckFragment extends Fragment {
    private FragmentHistoryCheckBinding binding;
    private ArrayList<UserCheck> uList;
    private CheckAdapter historyAdapter;


    public HistoryCheckFragment() {
    }

    public static HistoryCheckFragment newInstance() {
        HistoryCheckFragment fragment = new HistoryCheckFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_history_check, null , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uList = new ArrayList<>();
        binding.rvHistory.setHasFixedSize(true);
        binding.rvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));

        historyAdapter = new CheckAdapter(uList);
        binding.rvHistory.setAdapter(historyAdapter);
        FirebaseUsers.getInstance().getAllUserCheck("UserCheck").addOnCompleteListener(new OnCompleteListener<List<UserCheck>>() {
            @Override
            public void onComplete(@NonNull Task<List<UserCheck>> task) {
                if(task.isSuccessful()) {
                    uList.addAll(task.getResult());
                    historyAdapter.notifyDataSetChanged();
                }
                else{
                    Exception ex = task.getException();
                }
            }
        });

    binding.btnBack.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Navigation.findNavController(view).navigate(R.id.homeFragment);
        }
    });

        FirebaseDatabase mDatabase;
        mDatabase = FirebaseDatabase.getInstance();
        mDatabase.getReference("check").setValue(false);
    }
}