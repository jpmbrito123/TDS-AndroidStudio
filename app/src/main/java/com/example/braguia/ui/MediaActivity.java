package com.example.braguia.ui;

import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;


import com.example.braguia.R;
import com.example.braguia.data.Media;
import com.example.braguia.utils.ApiService;
import com.example.braguia.viewmodel.MediaViewModel;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MediaActivity extends AppCompatActivity {

    private LinearLayout view;
    private MediaViewModel mvm;
    private Map<Integer, Media> medias;
    private Map<Integer, MediaPlayer> mediaPlayers = new HashMap<>();

    private Map<Integer, SimpleExoPlayer> exoPlayers = new HashMap<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);

        setTitle("Media");

        view = findViewById(R.id.mediaContainer);
        mvm = new ViewModelProvider(this).get(MediaViewModel.class);


        LiveData<List<Media>> mediaLiveData = mvm.medias;
        mediaLiveData.observe(this, mediaList -> {
            if (mediaList != null && !mediaList.isEmpty()) {
                medias = new HashMap<>();
                for (Media m : mediaList)
                    medias.put(m.getId(), m);
                try {
                    setMedia();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void setMedia() throws IOException {

        view.setDividerDrawable(AppCompatResources.getDrawable(this, R.drawable.divider));
        view.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);

        if (medias.size()==0) {
            TextView text = new TextView(this);
            text.setText(R.string.medianotFound);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(20, 20, 10, 10);
            text.setLayoutParams(params);

            view.addView(text);
            return;
        }

        medias.forEach((k, v) -> {
            switch (v.getMediaType()) {
                case "I":                // image
                    imageSetup(v);
                    break;

                case "V":                // video
                    videoSetup(v);
                    break;

                case "R":                // audio
                    try {
                        audioSetup(v);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        });

        Button download = createDownloadBtn();
        view.addView(download);
    }

    public Button createDownloadBtn () {
        Button download = new Button(this);

        download.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        download.setText(R.string.mediadownloadAllMedia);
        download.setTextColor(getResources().getColor(R.color.white, null));
        download.setBackgroundColor(getResources().getColor(R.color.purple_700, null));
        download.setOnClickListener(view -> downloadAllMedia());

        return download;
    }

    public void downloadAllMedia(){

        Toast.makeText(this, "Downloading media...", Toast.LENGTH_SHORT).show();
        medias.forEach((k, v) -> {
            if(v.getMediaType().equals("I")) mvm.downloadImage(v);
            else mvm.download(v);
        });
        Toast.makeText(this, "Download complete!", Toast.LENGTH_SHORT).show();

    }


    private void imageSetup(Media v) {
        LinearLayout linear = new LinearLayout(this);
        linear.setOrientation(LinearLayout.VERTICAL);
        linear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linear.setGravity(Gravity.CENTER);
        Log.d("MediaActivity", "Image path: " + v.getMediaFile());
        ImageView image = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(view.getWidth(), 1000);
        image.setLayoutParams(params);

        String imageUrl = ApiService.getInstance(getApplication()).getUrl() + v.getMediaFile();
        String filename = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        File file = new File(this.getFilesDir(), filename);

        if (file.exists())
            image.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        else
            Picasso.get().load(imageUrl).into(image);

        linear.addView(image);
        view.addView(linear);
    }

    public void videoSetup(Media v) {

        String url = ApiService.getInstance(getApplication()).getUrl() + v.getMediaFile();
        String filename = url.substring(url.lastIndexOf("/") + 1);
        String pathToLocal = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/" + filename;
        File file = new File(pathToLocal);

        LinearLayout shell = new LinearLayout(this);
        shell.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        PlayerView playerView = new PlayerView(this);
        LinearLayout.LayoutParams playerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        playerView.setLayoutParams(playerParams);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);


        SimpleExoPlayer player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        playerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                playerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (file.exists()) {
                    player.setMediaItem(MediaItem.fromUri(Uri.fromFile(file)));
                } else {
                    player.setMediaItem(MediaItem.fromUri(Uri.parse(url)));
                }
                player.prepare();
                player.setPlayWhenReady(true);
            }
        });

        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_ENDED) {
                    player.seekTo(0);
                    player.setPlayWhenReady(true);
                }
            }
        });

        shell.addView(playerView);


        view.addView(shell);
    }




    public void audioSetup(Media media) throws IOException {
        int id = media.getId();

        LinearLayout shell = new LinearLayout(this);
        shell.setOrientation(LinearLayout.VERTICAL);
        shell.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        shell.setGravity(Gravity.CENTER);

        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        String url = ApiService.getInstance(getApplication()).getUrl() + media.getMediaFile();
        String filename = url.substring(url.lastIndexOf("/") + 1);
        String pathToLocal = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/" + filename;

        File file = new File(pathToLocal);

        if(file.exists())
            mediaPlayer.setDataSource(pathToLocal);
        else
            mediaPlayer.setDataSource(url);

        mediaPlayer.prepare();
        mediaPlayers.put(id, mediaPlayer);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.setGravity(Gravity.CENTER);

        Button playAudio = new Button(this);
        playAudio.setText(R.string.mediaplayAudio);

        Button pauseAudio = new Button(this);
        pauseAudio.setText(R.string.mediapauseAudio);

        playAudio.setOnClickListener(v -> {
            mediaPlayers.get(id).start();
            Toast.makeText(MediaActivity.this, "Audio Started", Toast.LENGTH_SHORT).show();
        });

        pauseAudio.setOnClickListener(view -> {
            mediaPlayers.get(id).pause();
            Toast.makeText(MediaActivity.this, "Audio Paused", Toast.LENGTH_SHORT).show();
        });

        layout.addView(playAudio);
        layout.addView(pauseAudio);


        shell.addView(layout);
        view.addView(shell);
    }



    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayers.forEach((k, v) -> v.release());
        mediaPlayers = new HashMap<>();
    }

    @Override
    public void onDestroy () {
        super.onDestroy();
        mediaPlayers.forEach((k, v) -> v.release());
        mediaPlayers = new HashMap<>();
    }
}