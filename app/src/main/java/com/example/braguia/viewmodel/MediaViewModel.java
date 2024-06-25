package com.example.braguia.viewmodel;

import android.app.Application;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.braguia.data.Media;
import com.example.braguia.repository.MediaRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class MediaViewModel extends AndroidViewModel {

    private Application app;
    private MediaRepository mediaRepository;
    public LiveData<List<Media>> medias;


    public MediaViewModel(@NonNull Application application) {
        super(application);
        app= application;
        mediaRepository = new MediaRepository(application);
        medias = mediaRepository.getAllMedias();
    }


    public void downloadImage(Media m){

        String imageUrl = "http://192.168.85.186" + m.getMediaFile();

        String filename = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        FileOutputStream fos = null;
        try {
            URL url = new URL(imageUrl);
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            Bitmap image = BitmapFactory.decodeStream(inputStream);

            fos = app.openFileOutput(filename, Context.MODE_PRIVATE);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void download(Media m){

        String url = "http://192.168.85.186" + m.getMediaFile();
        String filename = url.substring(url.lastIndexOf("/") + 1);

        DownloadManager downloadManager = (DownloadManager) app.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle("My Download");
        request.setDescription("Downloading file...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(app, Environment.DIRECTORY_DOWNLOADS, filename);
        downloadManager.enqueue(request);

    }
    



}