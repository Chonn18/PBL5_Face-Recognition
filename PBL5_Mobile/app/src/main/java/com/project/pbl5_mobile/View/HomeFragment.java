package com.project.pbl5_mobile.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
import com.project.pbl5_mobile.Model.Entity.Class;
import com.project.pbl5_mobile.Model.Helper.FirebaseClasses;
import com.project.pbl5_mobile.Model.Helper.IClickItemClassListener;
import com.project.pbl5_mobile.R;
import com.project.pbl5_mobile.ViewModel.ClassAdapter;
import com.project.pbl5_mobile.databinding.FragmentHomeBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ArrayList<String> classList;
    private ArrayList<Class> cList;
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

//        FirebaseDatabase mDatabase;
//        mDatabase = FirebaseDatabase.getInstance();
//        mDatabase.getReference("check").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Boolean isChecked = snapshot.getValue(Boolean.class);
//                if(isChecked){
//                    Navigation.findNavController(view).navigate(R.id.historyCheckFragment);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        cList = new ArrayList<>();
        binding.rvClass.setHasFixedSize(true);
        binding.rvClass.setLayoutManager(new LinearLayoutManager(getActivity()));

        clasAdapter = new ClassAdapter(cList, new IClickItemClassListener() {
            @Override
            public void onClickItem(Class clas) {


                Bundle bundle = new Bundle();
                bundle.putSerializable("class", (Serializable) clas);
                ClassFragment classFragment = new ClassFragment();
                classFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, classFragment)
                        .addToBackStack(null)
                        .commit();

            }
        });
        binding.rvClass.setAdapter(clasAdapter);


        FirebaseClasses.getInstance().getClass("Class").addOnCompleteListener(new OnCompleteListener<List<Class>>() {
            @Override
            public void onComplete(@NonNull Task<List<Class>> task) {
                if(task.isSuccessful()) {
                    cList.addAll(task.getResult());
//                    classList.addAll(cList.g)
                    clasAdapter.notifyDataSetChanged();
                }
                else{
                    Exception ex = task.getException();
                }
            }
        });

        binding.btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.history1Fragment);
            }
        });

        binding.btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.cameraFragment);
            }
        });
    }


    private void onClickGoToClass(Class c, View v){
        Navigation.findNavController(v).navigate(R.id.classFragment);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Object_class", c.getIdclass());

    }
}