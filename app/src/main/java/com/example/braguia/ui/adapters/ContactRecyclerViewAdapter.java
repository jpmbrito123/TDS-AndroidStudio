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
import com.example.braguia.data.Contact;

import java.util.List;

public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ViewHolder> {
    private List<Contact> mValues;
    private Context mContext;

    public ContactRecyclerViewAdapter(List<Contact> contacts, Context context) {
        this.mValues = contacts;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contacts_rv_item, parent, false);
        return new ContactRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = mValues.get(position);


        holder.nameView.setText(contact.getContactName());
        holder.phoneView.setText(contact.getContactPhone());
        holder.urlView.setText(contact.getUrl());
        holder.mailView.setText(contact.getEmail());
        holder.descView.setText(contact.getDescription());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + contact.getContactPhone()));
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
            nameView = itemView.findViewById(R.id.contact_name);
            phoneView = itemView.findViewById(R.id.contact_phone);
            urlView = itemView.findViewById(R.id.contact_url);
            mailView = itemView.findViewById(R.id.contact_mail);
            descView = itemView.findViewById(R.id.contact_desc);
            button = itemView.findViewById(R.id.contact_btn);
        }
    }
}
