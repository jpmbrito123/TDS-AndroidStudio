package com.example.braguia.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.braguia.R;
import com.example.braguia.data.Social;

import java.util.List;

public class SocialRecyclerViewAdapter extends RecyclerView.Adapter<SocialRecyclerViewAdapter.ViewHolder> {
    private List<Social> mValues;

    public SocialRecyclerViewAdapter(List<Social> socials) {
        this.mValues = socials;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.socials_rv_item, parent, false);
        return new SocialRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Social social = mValues.get(position);

        holder.nameView.setText(social.getSocialName());
        holder.urlView.setText(social.getUrl());
        holder.linkView.setText(social.getShareLink());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        private TextView urlView;
        private TextView linkView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.social_name);
            urlView = itemView.findViewById(R.id.social_url);
            linkView = itemView.findViewById(R.id.social_link);
        }
    }
}
