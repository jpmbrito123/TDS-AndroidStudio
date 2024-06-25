package com.example.braguia.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.braguia.R;
import com.example.braguia.data.Partner;

import java.util.List;

public class PartnerRecyclerViewAdapter extends RecyclerView.Adapter<PartnerRecyclerViewAdapter.ViewHolder> {
    private List<Partner> mValues;
    private Context mContext;

    public PartnerRecyclerViewAdapter(List<Partner> Partners, Context context) {
        this.mValues = Partners;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.partners_rv_item, parent, false);
        return new PartnerRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Partner Partner = mValues.get(position);


        holder.nameView.setText(Partner.getPartnerName());
        holder.phoneView.setText(Partner.getPartnerPhone());
        holder.urlView.setText(Partner.getUrl());
        holder.mailView.setText(Partner.getEmail());
        holder.descView.setText(Partner.getDescription());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + Partner.getPartnerPhone()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(callIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameView;
        private TextView phoneView;
        private TextView urlView;
        private TextView mailView;
        private TextView descView;
        private Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.partner_name);
            phoneView = itemView.findViewById(R.id.partner_phone);
            urlView = itemView.findViewById(R.id.partner_url);
            mailView = itemView.findViewById(R.id.partner_mail);
            descView = itemView.findViewById(R.id.partner_desc);
            button = itemView.findViewById(R.id.partner_btn);
        }
    }
}
