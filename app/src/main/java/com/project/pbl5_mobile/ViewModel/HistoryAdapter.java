package com.project.pbl5_mobile.ViewModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.pbl5_mobile.Model.Entity.User;
import com.project.pbl5_mobile.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<User> listUser;
    private OnPersonListener onPersonListener;

    public HistoryAdapter(List<User> listUser,OnPersonListener onPersonListener){
        this.listUser=listUser;
        this.onPersonListener=onPersonListener;

    }
    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);

        return new ViewHolder(view,onPersonListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        User user = listUser.get(position);
        holder.tvName.setText(user.getName());
        holder.tvTime.setText(user.getTime());
        if(!user.getAvatar().isEmpty())
            Picasso.get().load(user.getAvatar()).into(holder.ivHistoryImage);



    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivHistoryImage;
        private TextView tvName, tvTime;
        OnPersonListener onPersonListener;
        public ViewHolder(View view,OnPersonListener onPersonListener) {
            super(view);
            // Define click listener for the ViewHolder's View
            ivHistoryImage = view.findViewById(R.id.iv_person);
            tvName = view.findViewById(R.id.tv_name);
            tvTime = view.findViewById(R.id.tv_time);
            this.onPersonListener=onPersonListener;
            view.setOnClickListener(this);
            // textView = (TextView) view.findViewById(R.id.textView);
        }
        @Override
        public void onClick(View view) {
            onPersonListener.onPersonClick(getAdapterPosition());

        }


//        public TextView getTextView() {
//            return textView;
//        }
    }
    public interface OnPersonListener{
        void onPersonClick(int position);
    }
    public void updateList(ArrayList<User> newList){
        listUser = new ArrayList<>();
        listUser.addAll(newList);
        notifyDataSetChanged();
    }
}