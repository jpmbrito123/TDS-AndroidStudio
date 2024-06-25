package com.example.braguia.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.braguia.R;
import com.example.braguia.data.HistoryRecord;
import com.example.braguia.data.RelTrail;

import java.util.List;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {
    private final List<HistoryRecord> mValues;

    public HistoryRecyclerViewAdapter(List<HistoryRecord> items ) { mValues = items; }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_history_item, parent, false);
        return new HistoryRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoryRecyclerViewAdapter.ViewHolder holder, int position) {
        HistoryRecord record = mValues.get(position);

        holder.mTrailName.setText(mValues.get(position).getTrail().getName());
        holder.mDate.setText(mValues.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

public static class ViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView mTrailName;
    public final TextView mDate;

    public ViewHolder(View view) {
        super(view);
        mView = view;
        mTrailName = view.findViewById(R.id.history_trailName);
        mDate = view.findViewById(R.id.history_traildate);
    }

    @Override
    public String toString() {
        return super.toString() + mTrailName + mDate;
    }
    }
}