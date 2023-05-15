package com.project.pbl5_mobile.ViewModel;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.project.pbl5_mobile.Model.Entity.User;
import com.project.pbl5_mobile.Model.Entity.UserCheck;
import com.project.pbl5_mobile.R;
import com.project.pbl5_mobile.databinding.CheckItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CheckAdapter extends RecyclerView.Adapter<CheckAdapter.MyViewHolder>{
    private List<UserCheck> listUser;

    public CheckAdapter(List<UserCheck> listUser){
        this.listUser=listUser;

    }

    @NonNull
    @Override
    public CheckAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CheckItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.history_item, parent, false);
        return new CheckAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserCheck user = listUser.get(position);
        if(user.getId()==null){
            holder.binding.tvId.setText("Unknow");
        }
        else holder.binding.tvId.setText(user.getId().toString());
        if(user.getName()==null){
            holder.binding.tvName.setText("Unknow");
        }
        else holder.binding.tvName.setText(user.getName());
        holder.binding.tvTime.setText(user.getTime());
        if(!user.getUrlimg().isEmpty())
            Picasso.get().load(user.getUrlimg()).into(holder.binding.ivPerson);
        if(user.getIscheck()== true)
            holder.binding.tvIscheck.setText("Da check in hom nay");
        else holder.binding.tvIscheck.setText("Chua check in");
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public CheckItemBinding binding;
        public MyViewHolder(CheckItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
