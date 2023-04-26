package com.project.pbl5_mobile.ViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.project.pbl5_mobile.Model.Entity.Student;
import com.project.pbl5_mobile.Model.Helper.IClickStudentListener;
import com.project.pbl5_mobile.R;
import com.project.pbl5_mobile.databinding.StudentItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StudentAdapter extends  RecyclerView.Adapter<StudentAdapter.MyViewHolder>{

    private List<Student> liststudent;
    private IClickStudentListener clickStudentListener;


    public StudentAdapter(List<Student> liststudent){this.liststudent = liststudent;}

    public StudentAdapter(List<Student> liststudent,IClickStudentListener clickStudentListener ){
        this.liststudent = liststudent;
        this.clickStudentListener = clickStudentListener;
    }

    @NonNull
    @Override
    public StudentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        StudentItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.student_item, parent, false);
        return new MyViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.MyViewHolder holder, int position) {
        Student s = liststudent.get(position);

        holder.binding.tvId.setText(s.getId().toString());
        holder.binding.tvName.setText(s.getName());
        holder.binding.tvDate.setText(s.getDate());
        holder.binding.tvSex.setText(s.getSex());
        if(!s.getAvatar().isEmpty())
            Picasso.get().load(s.getAvatar()).into(holder.binding.ivPerson);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickStudentListener.onclickStudent(s);
            }
        });

    }

    @Override
    public int getItemCount() {
        return liststudent.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public StudentItemBinding binding;

        MyViewHolder(StudentItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }

    }

}
