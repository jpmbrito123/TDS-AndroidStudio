package com.example.braguia.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.braguia.R;
import com.example.braguia.data.Edge;
import com.example.braguia.data.Pin;

import java.util.List;

public class TrailEdgeRecyclerViewAdapter extends RecyclerView.Adapter<TrailEdgeRecyclerViewAdapter.ViewHolder> {
    private final List<Edge> mValues;

    public TrailEdgeRecyclerViewAdapter(List<Edge> edges) {
        this.mValues = edges;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trail_edge_recyclerview_item, parent, false);
        return new TrailEdgeRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Edge edge = mValues.get(position);
        Pin start_pin = edge.getStart_pin();
        Pin end_pin = edge.getEnd_pin();

        holder.nameView.setText(String.format("%s -> %s", start_pin.getName(), end_pin.getName()));

        String desc = edge.getDescription();
        if (desc.equals("n.a") || desc.equals("n.a.")) {
            desc = "No additional details";
        }
        holder.descView.setText(desc);

        String transport = edge.getTransport();
        switch (transport) {
            case "D":
                transport = "Driving";
                break;
            case "W":
                transport = "Walking";
                break;
            default:
                Log.d("Siu", "Unexpected error");
                break;
        }

        holder.transportDurationView.setText(String.format("%s: %s minutes", transport, edge.getDuration()));

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameView;
        private final TextView descView;
        private final TextView transportDurationView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.edge_name);
            descView = itemView.findViewById(R.id.edge_desc);
            transportDurationView = itemView.findViewById(R.id.edge_td);
        }
    }
}
