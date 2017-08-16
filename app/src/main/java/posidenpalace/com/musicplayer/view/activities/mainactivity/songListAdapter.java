package posidenpalace.com.musicplayer.view.activities.mainactivity;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import posidenpalace.com.musicplayer.R;
import posidenpalace.com.musicplayer.model.MusicList;

public class songListAdapter extends RecyclerView.Adapter<songListAdapter.ViewHolder> {
    List<MusicList> songsList = new ArrayList<>();

    public songListAdapter(List<MusicList> songList) {
        this.songsList = songList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MusicList song = songsList.get(position);
        holder.title.setText(song.getTitle());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent songPath = new Intent("path");
                songPath.putExtra("position",""+position);
                songPath.putExtra("path", song.getPath());
                view.getContext().sendBroadcast(songPath);
            }
        });

    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView title;
        public ViewHolder(View itemView) {
            super(itemView);
            title =  itemView.findViewById(R.id.tvILSongName);
        }
    }
}
