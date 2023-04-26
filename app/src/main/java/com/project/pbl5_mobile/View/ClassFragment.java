package com.project.pbl5_mobile.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.project.pbl5_mobile.Model.Entity.Class;
import com.project.pbl5_mobile.Model.Entity.Student;
import com.project.pbl5_mobile.Model.Helper.FirebaseClasses;
import com.project.pbl5_mobile.Model.Helper.FirebaseStrudents;
import com.project.pbl5_mobile.Model.Helper.IClickStudentListener;
import com.project.pbl5_mobile.R;
import com.project.pbl5_mobile.ViewModel.ClassAdapter;
import com.project.pbl5_mobile.ViewModel.StudentAdapter;
import com.project.pbl5_mobile.databinding.FragmentClassBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ClassFragment extends Fragment {
    private FragmentClassBinding binding;
    private ArrayList<Student> sList;
    private Class c;
    private StudentAdapter sAdapter;
    private String id;


    public ClassFragment() {
        // Required empty public constructor
    }


    public static ClassFragment newInstance() {
        ClassFragment fragment = new ClassFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            c = (Class) getArguments().getSerializable("class");
//            sList = (ArrayList<Student>) getArguments().getSerializable("Students");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_class, container , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tvClassname.setText(c.getClassname().toString());
        Integer id = (c.getIdclass());

        sList = new ArrayList<>();
        binding.rvStudent.setHasFixedSize(true);
        binding.rvStudent.setLayoutManager(new LinearLayoutManager(getActivity()));

        sAdapter = new StudentAdapter(sList, new IClickStudentListener() {
            @Override
            public void onclickStudent(Student student) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("student", (Serializable) student);
                History1Fragment history1Fragment = new History1Fragment();
                history1Fragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, history1Fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
//        sAdapter.notifyDataSetChanged();
        binding.rvStudent.setAdapter(sAdapter);

        if(id==null){
            Toast.makeText(getContext(),"idclass null",Toast.LENGTH_SHORT).show();
        }

        FirebaseStrudents.getInstance().getStudentByClassid("Student",id).addOnCompleteListener(new OnCompleteListener<List<Student>>() {
            @Override
            public void onComplete(@NonNull Task<List<Student>> task) {
                if(task.isSuccessful()) {
                    sList.addAll(task.getResult());
                    sAdapter.notifyDataSetChanged();
                }
                else{
                    Exception ex = task.getException();
                }
            }
        });
//        FirebaseStrudents.getInstance().getAllStudent("Student").addOnCompleteListener(new OnCompleteListener<List<Student>>() {
//            @Override
//            public void onComplete(@NonNull Task<List<Student>> task) {
//                if(task.isSuccessful()) {
//                    sList.addAll(task.getResult());
//                    sAdapter.notifyDataSetChanged();
//                }
//                else{
//                    Exception ex = task.getException();
//                }
//            }
//        });



    }
}