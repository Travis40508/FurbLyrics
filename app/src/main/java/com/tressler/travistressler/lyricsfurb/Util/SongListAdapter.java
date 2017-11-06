package com.tressler.travistressler.lyricsfurb.Util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tressler.travistressler.lyricsfurb.R;
import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongEntity;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by travistressler on 11/5/17.
 */

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongViewHolder> {

    private List<SongEntity> songList;

    public SongListAdapter(List<SongEntity> songList) {
        this.songList = songList;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song_listing, parent, false);
        return new SongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        holder.bindView(songList.get(position));
    }

    @Override
    public int getItemCount() {
        if(songList != null) {
            return songList.size();
        } else {
            return 0;
        }
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_artist_name)
        protected TextView artistName;

        @BindView(R.id.item_song_title)
        protected TextView songTitle;


        public SongViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(SongEntity songEntity) {
            artistName.setText(songEntity.getSongArtist());
            songTitle.setText(songEntity.getSongTitle());
        }
    }
}
