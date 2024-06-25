package com.example.braguia.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.braguia.R;
import com.example.braguia.data.User;
import com.squareup.picasso.Picasso;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder> {
    private final User mValue;

    public UserRecyclerViewAdapter(User item) {mValue = item;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_recycler_view, parent, false);
        return new UserRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameView.setText(mValue.getUsername());
        holder.emailView.setText(String.format("Email: %s", mValue.getEmail()));
        holder.typeView.setText(String.format("Type: %s", mValue.getUser_type()));
        Picasso.get()
                .load("https://upload.wikimedia.org/wikipedia/commons/7/79/Moussa_Marega_2018.jpg")
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView nameView;
        private final TextView emailView;
        private final TextView typeView;

        public ViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.profile_image);
            nameView = view.findViewById(R.id.profile_name);
            emailView = view.findViewById(R.id.profile_email);
            typeView = view.findViewById(R.id.profile_type);
        }
    }
}
