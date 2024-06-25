package com.example.braguia.ui.adapters;

import static com.example.braguia.data.RoomDb.databaseWriteExecutor;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.braguia.R;
import com.example.braguia.data.HistoryTrailDao;
import com.example.braguia.data.HistoryTrails;
import com.example.braguia.data.RoomDb;
import com.example.braguia.data.Trail;
import com.example.braguia.ui.TrailDetailActivity;
import com.example.braguia.data.TrailDao;
import com.example.braguia.repository.TrailRepository;
import com.example.braguia.utils.ApiService;
import com.example.braguia.utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TrailRecyclerViewAdapter extends RecyclerView.Adapter<TrailRecyclerViewAdapter.ViewHolder> {
    private final List<Trail> mValues;
    private HistoryTrailDao historyTrailDao;

    public TrailRecyclerViewAdapter(List<Trail> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Trail currentTrail = mValues.get(position);

        holder.mNameView.setText(mValues.get(position).getName());
        Picasso.get()
                .load(mValues.get(position).getUrl())
                .into(holder.imageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.mView.getContext();

                addHistory(context, currentTrail);

                Intent intent = new Intent(context, TrailDetailActivity.class);
                intent.putExtra("trail_id", currentTrail.getId());

                context.startActivity(intent);
            }
        });
    }

    private void addHistory(Context context, Trail trail){
        SessionManager sessionManager = new SessionManager(context.getApplicationContext());
        if (sessionManager.getSharedPreferences().getString("isLoggedIn", "false").equalsIgnoreCase("true")){
            RoomDb database = RoomDb.getInstance(context.getApplicationContext());
            String username = sessionManager.getUsername();
            historyTrailDao = database.HistoryTrailDao();

            HistoryTrails historyTrails = new HistoryTrails();
            historyTrails.setTrail_id(trail.getId());
            historyTrails.setUsername(username);
            Date now = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            String formattedDate = format.format(now);
            historyTrails.setDate(formattedDate);

            insert(historyTrails);
        }
    }

    public void insert(HistoryTrails historyTrails){
        new InsertAsyncTask(historyTrailDao).execute(historyTrails);
    }

    private static class InsertAsyncTask extends AsyncTask<HistoryTrails, Void, Void> {
        private HistoryTrailDao historyTrailDao;

        public InsertAsyncTask(HistoryTrailDao catDao) {
            this.historyTrailDao = catDao;
        }

        @Override
        protected Void doInBackground(HistoryTrails... historyTrails) {
            int rowCount = historyTrailDao.getInstance(historyTrails[0].getUsername(), historyTrails[0].getTrail_id(), historyTrails[0].getDate());
            if (rowCount == 0){
                historyTrailDao.insert(historyTrails[0]);
            }
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.trail_name);
            imageView = view.findViewById(R.id.cardimage);
        }

        @Override
        public String toString() {
            return super.toString() + mNameView;
        }
    }
}
