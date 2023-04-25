package com.project.pbl5_mobile.ViewModel;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.project.pbl5_mobile.Model.Entity.User;
import com.project.pbl5_mobile.R;
import com.project.pbl5_mobile.databinding.HistoryItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {
    private List<User> listUser;


    public HistoryAdapter(List<User> listUser){
        this.listUser=listUser;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HistoryItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.history_item, parent, false);
        return new HistoryAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = listUser.get(position);
        holder.binding.tvId.setText(user.getId().toString());
        holder.binding.tvName.setText(user.getName());
        holder.binding.tvTime.setText(user.getTime());
        if(!user.getAvatar().isEmpty())
            Picasso.get().load(user.getAvatar()).into(holder.binding.ivPerson);



    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

//    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        private ImageView ivHistoryImage;
//        private TextView tvName, tvTime;
//        OnPersonListener onPersonListener;
//        public ViewHolder(View view) {
//            super(view);
//            // Define click listener for the ViewHolder's View
//            ivHistoryImage = view.findViewById(R.id.iv_person);
//            tvName = view.findViewById(R.id.tv_name);
//            tvTime = view.findViewById(R.id.tv_time);
//            this.onPersonListener=onPersonListener;
//            view.setOnClickListener(this);
//        }
//        @Override
//        public void onClick(View view) {
//            onPersonListener.onPersonClick(getAdapterPosition());
//
//        }


//        public TextView getTextView() {
//            return textView;
//        }
//    }
    public interface OnPersonListener{
        void onPersonClick(int position);
    }
    public void updateList(ArrayList<User> newList){
        listUser = new ArrayList<>();
        listUser.addAll(newList);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public HistoryItemBinding binding;
        public MyViewHolder(HistoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}