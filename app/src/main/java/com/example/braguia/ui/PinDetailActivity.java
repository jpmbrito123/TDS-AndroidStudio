package com.example.braguia.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.braguia.R;
import com.example.braguia.data.Media;

import com.example.braguia.ui.adapters.PinRelRecyclerViewAdapter;
import com.example.braguia.viewmodel.PinViewModel;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PinDetailActivity extends AppCompatActivity {
    private Map<Integer, MediaPlayer> mediaPlayers = new HashMap<>();
    private LinearLayout view;
    private Map<Integer, Media> medias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pin_details);

        TextView nameView = findViewById(R.id.pin_detail_name);
        TextView descView = findViewById(R.id.pin_detail_desc);
        TextView coordView = findViewById(R.id.pin_detail_coords);
        FloatingActionButton floatingActionButton = findViewById(R.id.pin_detail_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PinDetailActivity.this, ContactsActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String pinId = "";
        if (intent != null) {
            pinId = intent.getStringExtra("pin_id");
            // Log.d("Siu", "ID: " + pinId);
        }

        PinViewModel pinViewModel = new ViewModelProvider(this).get(PinViewModel.class);
        pinViewModel.getPin(pinId).observe(this, pin -> {
            nameView.setText(pin.getName());
            descView.setText(pin.getDescription());
            coordView.setText(String.format("Coordinates: %s, %s; %sm altitude", pin.getLatitude(), pin.getLongitude(), pin.getAltitude()));

            int pinRels = pin.getRelPins().size();
            if (pinRels > 0) {
                RecyclerView recyclerView = findViewById(R.id.pin_rel_fragment);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(new PinRelRecyclerViewAdapter(pin.getRelPins()));
            }



        });
        assert pinViewModel.getMedia(pinId) != null;
        view = findViewById(R.id.mediaContainer1);
        pinViewModel.getMedia(pinId).observe(this,pin->{
            if (pin != null && !pin.isEmpty()) {
                medias = new HashMap<>();
                for (Media m : pin)
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

        medias.forEach((v, k) -> {
            switch (k.getMediaType()) {
                case "I":                // image
                    imageSetup(k);
                    break;

                case "V":                // video
                    videoSetup(k);
                    break;

                case "R":                // audio
                    try {
                        audioSetup(k);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    break;
            }
        });

    }
    private void imageSetup(Media v) {
        LinearLayout linear = new LinearLayout(this);
        linear.setOrientation(LinearLayout.VERTICAL);
        linear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linear.setGravity(Gravity.CENTER);
        Log.d("MediaActivity", "Image path: " + v.getMediaFile());
        ImageView image = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        image.setLayoutParams(params);

        String imageUrl = "http://192.168.85.186" + v.getMediaFile();
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
        String url = "http://192.168.85.186" + v.getMediaFile();
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


        String url = "http://192.168.85.186"+ media.getMediaFile();
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
            Toast.makeText(PinDetailActivity.this, "Audio Started", Toast.LENGTH_SHORT).show();
        });

        pauseAudio.setOnClickListener(view -> {
            mediaPlayers.get(id).pause();
            Toast.makeText(PinDetailActivity.this, "Audio Paused", Toast.LENGTH_SHORT).show();
        });

        layout.addView(playAudio);
        layout.addView(pauseAudio);


        shell.addView(layout);
        view.addView(shell);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
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
