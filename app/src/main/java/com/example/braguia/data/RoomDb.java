package com.example.braguia.data;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {App.class, Contact.class, Edge.class, Media.class, Partner.class, Pin.class, RelPin.class, RelTrail.class, Social.class, Trail.class, User.class, HistoryTrails.class}, version = 23)
@TypeConverters({ContactTypeConverter.class, EdgeTypeConverter.class, PartnerTypeConverter.class, PinTypeConverter.class, RelPinTypeConverter.class, RelTrailTypeConverter.class, SocialTypeConverter.class, TrailConverter.class})
public abstract class RoomDb extends RoomDatabase {
    private static final String DATABASE_NAME = "BraGuia";
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);
    public abstract AppDao appDao();
    public abstract PinDao pinDao();
    public abstract TrailDao trailDao();
    public abstract UserDao userDao();
    public abstract  HistoryTrailDao HistoryTrailDao();
    public abstract MediaDao mediaDao();



    public static volatile RoomDb INSTANCE = null;

    public static RoomDb getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, RoomDb.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static Callback callback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsyn(INSTANCE);
        }
    };

    static class PopulateDbAsyn extends AsyncTask<Void,Void,Void>{
        private AppDao appDao;
        private MediaDao mediaDao;
        private PinDao pinDao;
        private TrailDao trailDao;
        private UserDao userDao;
        private HistoryTrailDao historyTrailDao;

        public PopulateDbAsyn(RoomDb catDatabase) {
            appDao = catDatabase.appDao();
            mediaDao = catDatabase.mediaDao();
            pinDao = catDatabase.pinDao();
            trailDao = catDatabase.trailDao();
            userDao = catDatabase.userDao();
            historyTrailDao = catDatabase.HistoryTrailDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            appDao.deleteAll();
            mediaDao.deleteAll();
            pinDao.deleteAll();
            trailDao.deleteAll();
            userDao.deleteAll();
            historyTrailDao.deleteAll();
            return null;
        }
    }
}
