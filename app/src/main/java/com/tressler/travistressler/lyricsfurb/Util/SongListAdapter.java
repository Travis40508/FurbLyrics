package com.tressler.travistressler.lyricsfurb.Util;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tressler.travistressler.lyricsfurb.R;
import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongEntity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by travistressler on 11/5/17.
 */

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongViewHolder> {

    private final Callback callback;
    private final String source;
    private List<SongEntity> songList;

    public SongListAdapter(List<SongEntity> songList, Callback callback, String source) {
        Collections.sort(songList, new Comparator<SongEntity>() {
            @Override
            public int compare(SongEntity songEntity, SongEntity t1) {
                return songEntity.getSongTitle().compareTo(t1.getSongTitle());
            }
        });
        this.songList = songList;
        this.callback = callback;
        this.source = source;
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song_listing, parent, false);
        return new SongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        holder.bindView(songList.get(position));
        holder.cardView.setOnClickListener(holder.onCellClicked(songList.get(position)));
    }

    @Override
    public int getItemCount() {
        if(songList != null) {
            return songList.size();
        } else {
            return 0;
        }
    }

    public void addSong(SongEntity songEntity) {
        if(source.equalsIgnoreCase("allSongsCreatePlaylist")) {
            songList.add(songEntity);
            Collections.sort(songList, new Comparator<SongEntity>() {
                @Override
                public int compare(SongEntity songEntity, SongEntity t1) {
                    return songEntity.getSongTitle().compareTo(t1.getSongTitle());
                }
            });
        } else if (source.equalsIgnoreCase("chosenSongsCreatePlaylist")) {
            songList.add(songEntity);
        }
        notifyDataSetChanged();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_artist_name)
        protected TextView artistName;

        @BindView(R.id.item_song_title)
        protected TextView songTitle;

        @BindView(R.id.card_view)
        protected CardView cardView;


        public SongViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(SongEntity songEntity) {
            artistName.setText(songEntity.getSongArtist());
            songTitle.setText(songEntity.getSongTitle());
        }

        public View.OnClickListener onCellClicked(SongEntity songEntity) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(source.equalsIgnoreCase("allSongsCreatePlaylist")) {
                        songList.remove(songEntity);
                        callback.onAllSongCellClicked(songEntity);
                    } else if(source.equalsIgnoreCase("chosenSongsCreatePlaylist")) {
                        songList.remove(songEntity);
                        callback.onChosenSongCellClicked(songEntity);
                    } else if (source.equalsIgnoreCase("allSongsList")) {
                        Log.d("@@@@", songEntity.getPlayLists().get(0));
                    }
                    notifyDataSetChanged();
                }
            };
        }
    }

    public interface Callback {
        void onChosenSongCellClicked(SongEntity songEntity);
        void onAllSongCellClicked(SongEntity songEntity);
    }
}
