package com.project.pbl5_mobile.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.project.pbl5_mobile.Model.Entity.Student;
import com.project.pbl5_mobile.Model.Entity.UserCheck;
import com.project.pbl5_mobile.Model.Helper.FirebaseStrudents;
import com.project.pbl5_mobile.R;
import com.project.pbl5_mobile.databinding.FragmentStartBinding;


public class StartFragment extends Fragment {

    private FragmentStartBinding binding;

    public StartFragment() {
        // Required empty public constructor
    }

    public static StartFragment newInstance(String param1, String param2) {
        StartFragment fragment = new StartFragment();

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
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.fragment_start, null, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.signInFragment);


//                Student s1 = new Student(103200006,103,"Thanh Trúc","1/1/2002",
//                        "https://firebasestorage.googleapis.com/v0/b/pbl5-c253c.appspot.com/o/anhmau%2Ftrucnho.jpg?alt=media&token=e968ee24-be21-437e-9706-a6119fef1737","female");
//                FirebaseStrudents.getInstance().addStudent(s1.getId(),s1);
//
//                Student s22 = new Student(103200007,103,"Thanh Trúc","24/11/2002",
//                        "https://firebasestorage.googleapis.com/v0/b/pbl5-c253c.appspot.com/o/anhmau%2Fttruc.jpg?alt=media&token=1b9c7d1e-bc56-4c1b-860a-8d60ae6bb92c","female");
//                FirebaseStrudents.getInstance().addStudent(s22.getId(),s22);
//                Student s2 = new Student(103200013,103,"Như Tâm","30/11/2002",
//                        "https://firebasestorage.googleapis.com/v0/b/pbl5-c253c.appspot.com/o/anhmau%2Fbolun.jpg?alt=media&token=236030ca-df6b-4613-ae5f-a6578e27539f","female");
//                FirebaseStrudents.getInstance().addStudent(s2.getId(),s2);
//                Student s3 = new Student(103200014,103,"Thu Thảo","23/10/2002",
//                        "https://firebasestorage.googleapis.com/v0/b/pbl5-c253c.appspot.com/o/anhmau%2Fthuthao.jpg?alt=media&token=cffef248-06f4-4fdc-af9c-0b7cb0a46c17","male");
//                FirebaseStrudents.getInstance().addStudent(s3.getId(),s3);
//
//                Student s44 = new Student(103200002,103,"Văn Chơn","1/1/2002",
//                        "https://firebasestorage.googleapis.com/v0/b/pbl5-c253c.appspot.com/o/IMG_8319.JPG?alt=media&token=cf6473ac-2176-4c1c-9767-8ece26c66f60","male");
//                FirebaseStrudents.getInstance().addStudent(s44.getId(),s44);
//
//                Student s4 = new Student(103200008,103,"Duy Khánh","5/11/2002",
//                        "https://firebasestorage.googleapis.com/v0/b/pbl5-c253c.appspot.com/o/anhmau%2F334233956_1248796882389779_3479889114478659290_n.jpg?alt=media&token=2119bf2e-2e0b-4dd9-9efb-f163982c3590","male");
//                FirebaseStrudents.getInstance().addStudent(s4.getId(),s4);
//
//                Student s5 = new Student(103200009,103,"Trần Lĩnh","21/11/2002",
//                        "https://firebasestorage.googleapis.com/v0/b/pbl5-c253c.appspot.com/o/anhmau%2Fz4156833795707_39256e109345cb37884c43afc3691100.jpg?alt=media&token=091ef125-f3de-44b2-ab1b-75810a467a90","male");
//                FirebaseStrudents.getInstance().addStudent(s5.getId(),s5);
//
//                Student s6 = new Student(103200011,103,"Ngọc Khải","7/4/2002",
//                        "https://firebasestorage.googleapis.com/v0/b/pbl5-c253c.appspot.com/o/anhmau%2Fz4158748982114_1450002ae6afd16a7b16e4d0efaa6b89.jpg?alt=media&token=fd96b400-8594-44fb-8b61-69ffdb09358c","male");
//                FirebaseStrudents.getInstance().addStudent(s6.getId(),s6);
//
//                Student s7 = new Student(103200012,103,"Trần Đình Đạt","3/10/2002",
//                        "https://firebasestorage.googleapis.com/v0/b/pbl5-c253c.appspot.com/o/anhmau%2Fz4158748908726_23303adb07f1698b3535671716c51c6e.jpg?alt=media&token=9292b65f-4e11-49ee-b92f-c8812f704930","male");
//                FirebaseStrudents.getInstance().addStudent(s7.getId(),s7);
            }
        });
    }
}