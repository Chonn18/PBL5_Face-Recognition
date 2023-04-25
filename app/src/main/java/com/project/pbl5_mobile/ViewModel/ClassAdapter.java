package com.project.pbl5_mobile.ViewModel;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.project.pbl5_mobile.Model.Entity.Class;
import com.project.pbl5_mobile.Model.Entity.Student;
import com.project.pbl5_mobile.Model.Helper.FirebaseClasses;
import com.project.pbl5_mobile.Model.Helper.FirebaseStrudents;
import com.project.pbl5_mobile.Model.Helper.IClickItemClassListener;
import com.project.pbl5_mobile.R;
import com.project.pbl5_mobile.View.ClassFragment;
import com.project.pbl5_mobile.databinding.ClassItemBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.MyViewHolder>{

    private List<Class> listclass;
    private IClickItemClassListener listener;
    private Context mContext;

    public ClassAdapter(List<Class> listclass){this.listclass = listclass;}

    public ClassAdapter(List<Class> listclass, IClickItemClassListener listener){
        this.listclass = listclass;
        this.listener = listener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ClassItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.class_item, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Class classname = listclass.get(position);
        holder.binding.tvNameClass.setText(classname.getClassname());

        List<String> temp = new ArrayList<>();
        final String idtemp = String.valueOf(classname.getIdclass());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickItem(classname);

//                Navigation.findNavController(v).navigate(R.id.classFragment);
            }
        });
        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickItem(classname);

//
//                String id = String.valueOf(classname.getIdclass());
//                FirebaseStrudents.getInstance().getStudentByClassid("Student",id).addOnCompleteListener(new OnCompleteListener<List<Student>>() {
//
//
//                    @Override
//                    public void onComplete(@NonNull Task<List<Student>> task) {
//                        if(task.isSuccessful()){
//                            List<Student> students =task.getResult();
//                            Bundle bundle = new Bundle();
//                            bundle.putSerializable("Students", (Serializable) students);
//                            ClassFragment createOrJoinFragment = new ClassFragment();
//                            createOrJoinFragment.setArguments(bundle);
//                            ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.classFragment, createOrJoinFragment)
//                                    .addToBackStack(null)
//                                    .commit();
////                            Navigation.findNavController(v).navigate(R.id.classFragment);
//                        }
//                    }
//                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return listclass.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ClassItemBinding binding;
        public ConstraintSet.Layout layout;
        public CardView layout_item;
        MyViewHolder(ClassItemBinding itemBinding) {

            super(itemBinding.getRoot());
            this.binding = itemBinding;
            this.layout_item = binding.layoutClassItem;
        }
    }
}
