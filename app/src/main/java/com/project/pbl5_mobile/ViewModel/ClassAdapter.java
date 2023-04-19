package com.project.pbl5_mobile.ViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.project.pbl5_mobile.Model.Entity.User;
import com.project.pbl5_mobile.R;
import com.project.pbl5_mobile.databinding.ClassItemBinding;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.MyViewHolder>{

    private List<String> listclass;

    public ClassAdapter(List<String> listclass){this.listclass = listclass;}


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ClassItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.class_item, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String classname = listclass.get(position);
        holder.binding.tvNameClass.setText(classname);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Navigation.findNavController(v).navigate(R.id.homeFragment);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listclass.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ClassItemBinding binding;
        MyViewHolder(ClassItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
