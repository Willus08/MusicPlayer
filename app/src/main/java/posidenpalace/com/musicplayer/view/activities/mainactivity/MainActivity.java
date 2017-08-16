package posidenpalace.com.musicplayer.view.activities.mainactivity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import posidenpalace.com.musicplayer.R;
import posidenpalace.com.musicplayer.model.MusicList;
import posidenpalace.com.musicplayer.view.activities.injection.mainactivity.DaggerMainActivityComponent;


public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    private static final String TAG = "test";
    @Inject
    MainActivityPresenter presenter;

    @BindView(R.id.rvMAmusicHolder)
    RecyclerView recyclerView;


    @BindView(R.id.btnMMPlay)
    Button play;

    MediaPlayer media = new MediaPlayer();
    songListAdapter adapt;
    DefaultItemAnimator itemAnimator;
    RecyclerView.LayoutManager layout;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    IntentFilter filter = new IntentFilter();
    int songIndex =0;
    String path= "";
    MyReciver reciver = new MyReciver();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupDagger();
        ButterKnife.bind(this);
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
        presenter.addView(this);
        presenter.getSongs();
    }

    private void setupMediaSource(String path) throws IOException {
        if (media.isPlaying()) {
            media.stop();
            media.reset();
        }

        media.setDataSource(this.getApplicationContext(), Uri.parse(path));
        media.prepare();
        startSong();

    }

    private void startSong() {
        media.start();
    }

    public void setupDagger(){
        DaggerMainActivityComponent.create().inject(this);
    }

    @Override
    public void showError(Error error) {

    }

    public void previousSong(View view) throws IOException {
        Log.d(TAG, "previousSong: ");
        if (songIndex-1 >= 0) {
            path = adapt.songsList.get(songIndex-1).getPath();
            songIndex--;
        }
        setupMediaSource(path);
    }

    public void playMusic(View view) throws IOException {
       if (media.isPlaying()){
           media.pause();
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
               play.setBackground(getDrawable(R.drawable.play_button));
           }

       }else {
           startSong();
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
               play.setBackground(getDrawable(R.drawable.pause_button));
           }


       }
    }

    public void nextSong(View view) throws IOException {
        Log.d(TAG, "nextSong: ");
        if (songIndex+1 < adapt.songsList.size()) {
            path = adapt.songsList.get(songIndex+1).getPath();
            songIndex++;
        }
        setupMediaSource(path);

    }

    public void stopMusic(View view) {
        media.stop();
    }

    @Override
    public void setupAdapter(List<MusicList> songList) {
        Log.d(TAG, "setupAdapter: " + songList);
        adapt = new songListAdapter(songList);
        layout = new LinearLayoutManager(this);
        itemAnimator = new DefaultItemAnimator();

        recyclerView.setAdapter(adapt);
        recyclerView.setLayoutManager(layout);
        recyclerView.setItemAnimator(itemAnimator);

    }

    @Override
    protected void onStart() {
        super.onStart();
        filter.addAction("path");
        filter.addAction("position");
        registerReceiver(reciver,filter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(reciver);
    }

    class MyReciver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
                songIndex = Integer.parseInt(intent.getStringExtra("position"));
                path = (intent.getStringExtra("path"));
            try {
                setupMediaSource(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
