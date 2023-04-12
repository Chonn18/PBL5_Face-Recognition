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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.project.pbl5_mobile.Model.User;
import com.project.pbl5_mobile.R;
import com.project.pbl5_mobile.databinding.FragmentSignInBinding;

import java.util.ArrayList;


public class SignInFragment extends Fragment {

    private FragmentSignInBinding binding;
    private FirebaseAuth fAuth;

    public ArrayList<User> listUser;
    private  static  ArrayList<User> newList=new ArrayList<>();

    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
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
        binding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.fragment_sign_in, null , false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=binding.edEmail.getText().toString();
                String pass=binding.edPassword.getText().toString();
                if(name.isEmpty()|| pass.isEmpty()){
                    Toast.makeText(getContext(),"Please Fill Out Application",Toast.LENGTH_SHORT).show();
                }
                else{
                    fAuth.signInWithEmailAndPassword(name,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(),"Log In Successful",
                                        Toast.LENGTH_SHORT).show();
                                Navigation.findNavController(view).navigate(R.id.activitymain);
//                                Intent intent=new Intent(getContext(), MainActivity.class);
//                                startActivity(intent);
                            }
                            else Toast.makeText(getContext(),"Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
}