package posidenpalace.com.musicplayer.view.activities.mainactivity;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import posidenpalace.com.musicplayer.model.MusicList;

public class MainActivityPresenter implements MainActivityContract.Presenter {
    private static final String TAG = "test";
    MainActivityContract.View view;
    final String MEDIA_PATH = "/sdcard/Music/";
    List<MusicList> songsList = new ArrayList<>();

    @Override
    public void addView(MainActivityContract.View view) {
        this.view = view;
    }

    @Override
    public void removeView() {
        this.view = null;
    }

    @Override
    public void getSongs() {
        // SDCard Path


            File home = new File(MEDIA_PATH);
        Log.d(TAG, "getSongs:" + home.listFiles().length );
            if (home.listFiles(new FileExtensionFilter()).length > 0) {
                for (File file : home.listFiles(new FileExtensionFilter())) {
                    // Adding each song to SongList
                    HashMap<String, String> song = new HashMap<String, String>();
                    song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
                    song.put("songPath", file.getPath());

                    songsList.add(new MusicList(song.get("songTitle"),song.get("songPath")));
                    Log.d(TAG, "getSongs: " + file.getName());
                }view.setupAdapter(songsList);
            }else {
                Toast.makeText((Context) this.view, "There is no music in your Music folder", Toast.LENGTH_SHORT).show();
            }
            // return songs list array


    }



    class FileExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }

}
