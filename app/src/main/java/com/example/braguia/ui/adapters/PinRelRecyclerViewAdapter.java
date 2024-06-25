package com.example.braguia.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.braguia.R;
import com.example.braguia.data.RelPin;

import java.util.List;

public class PinRelRecyclerViewAdapter extends RecyclerView.Adapter<PinRelRecyclerViewAdapter.ViewHolder> {
    private final List<RelPin> mValues;

    public PinRelRecyclerViewAdapter(List<RelPin> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pin_rel_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PinRelRecyclerViewAdapter.ViewHolder holder, int position) {
        RelPin relPin = mValues.get(position);
        holder.detailView.setText(String.format("%s: %s", relPin.getAttrib(), relPin.getValue()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView detailView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            detailView = view.findViewById(R.id.pin_detail);
        }

        @Override
        public String toString() {
            return super.toString() + detailView;
        }
    }
}
