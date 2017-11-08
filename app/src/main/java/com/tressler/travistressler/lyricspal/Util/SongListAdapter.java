package com.tressler.travistressler.lyricspal.Util;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tressler.travistressler.lyricspal.R;
import com.tressler.travistressler.lyricspal.Repository.lyricsdatabase.SongEntity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by travistressler on 11/5/17.
 */

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongViewHolder> {

    private final Callback callback;
    private final String source;
    private List<SongEntity> songList;
    private List<SongEntity> referenceList;

    public SongListAdapter(List<SongEntity> songList, Callback callback, String source) {
        if(!source.equalsIgnoreCase("playlist")) {
            Collections.sort(songList, new Comparator<SongEntity>() {
                @Override
                public int compare(SongEntity songEntity, SongEntity t1) {
                    return songEntity.getSongTitle().compareTo(t1.getSongTitle());
                }
            });
        }
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
        holder.songTitle.setOnClickListener(holder.onCellClicked(songList.get(position)));
        holder.artistName.setOnClickListener(holder.onCellClicked(songList.get(position)));
        holder.cardView.setOnLongClickListener(holder.onCellLongClicked(songList.get(position)));
        holder.songTitle.setOnLongClickListener(holder.onCellLongClicked(songList.get(position)));
        holder.artistName.setOnLongClickListener(holder.onCellLongClicked(songList.get(position)));
        holder.upArrow.setOnClickListener(holder.onUpArrowClicked(songList.get(position)));
        holder.downArrow.setOnClickListener(holder.onDownArrowClicked(songList.get(position)));
        holder.deleteButton.setOnClickListener(holder.onDeleteClicked(songList.get(position)));
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
        if(source.equalsIgnoreCase("allSongsCreatePlaylist") || source.equalsIgnoreCase("addToPlaylistOtherSongs")) {
            songList.add(songEntity);
            Collections.sort(songList, new Comparator<SongEntity>() {
                @Override
                public int compare(SongEntity songEntity, SongEntity t1) {
                    return songEntity.getSongTitle().compareTo(t1.getSongTitle());
                }
            });
        } else if (source.equalsIgnoreCase("chosenSongsCreatePlaylist") || source.equalsIgnoreCase("addToPlaylistPlaylist")) {
            songList.add(songEntity);
        }
        notifyDataSetChanged();
    }

    public void hideExtraOptions() {
        for(SongEntity song : songList) {
            song.setDeletingEnabled(false);
            song.setEditingEnabled(false);
            notifyDataSetChanged();
        }
    }



    public class SongViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_artist_name)
        protected TextView artistName;

        @BindView(R.id.item_song_title)
        protected TextView songTitle;

        @BindView(R.id.card_view)
        protected CardView cardView;

        @BindView(R.id.button_up_arrow)
        protected ImageView upArrow;

        @BindView(R.id.button_down_arrow)
        protected ImageView downArrow;

        @BindView(R.id.button_delete)
        protected ImageView deleteButton;


        public SongViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(SongEntity songEntity) {
            artistName.setText(songEntity.getSongArtist());
            songTitle.setText(songEntity.getSongTitle());
            if(songEntity.isEditingEnabled()) {
                upArrow.setVisibility(View.VISIBLE);
                downArrow.setVisibility(View.VISIBLE);

            } else {
                upArrow.setVisibility(View.GONE);
                downArrow.setVisibility(View.GONE);
            }

            if(songEntity.isDeletingEnabled()) {
                deleteButton.setVisibility(View.VISIBLE);
            } else {
                deleteButton.setVisibility(View.GONE);
            }
        }

        public View.OnClickListener onCellClicked(SongEntity songEntity) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(source.equalsIgnoreCase("allSongsCreatePlaylist") || source.equalsIgnoreCase("addToPlaylistOtherSongs")) {
                        songList.remove(songEntity);
                        callback.onAllSongCellClicked(songEntity);
                    } else if(source.equalsIgnoreCase("chosenSongsCreatePlaylist") || source.equalsIgnoreCase("addToPlaylistPlaylist")) {
                        songList.remove(songEntity);
                        callback.onChosenSongCellClicked(songEntity);
                    } else if (source.equalsIgnoreCase("allSongsList")) {
                        callback.onAllSongCellClicked(songEntity);
                    } else if (source.equalsIgnoreCase("playlist")) {
                        callback.onChosenSongCellClicked(songEntity);
                    }
                    notifyDataSetChanged();
                }
            };
        }

        public View.OnLongClickListener onCellLongClicked(SongEntity songEntity) {
            return new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(source.equalsIgnoreCase("playlist")) {
                        for(SongEntity song : songList) {
                            song.setEditingEnabled(true);
                            song.setDeletingEnabled(true);
                            notifyDataSetChanged();
                        }
                    } else if (source.equalsIgnoreCase("allSongsList")) {
                        for(SongEntity song : songList) {
                            song.setDeletingEnabled(true);
                            notifyDataSetChanged();
                        }
                    }
                    callback.onCellLongClicked();
                    return false;
                }
            };
        }

        public View.OnClickListener onUpArrowClicked(SongEntity songEntity) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int indexOfSong = songList.indexOf(songEntity);
                    if(indexOfSong > 0) {
                        songList.remove(songEntity);
                        songList.add(indexOfSong - 1, songEntity);
                        notifyDataSetChanged();
                    }
                }
            };
        }

        public View.OnClickListener onDownArrowClicked(SongEntity songEntity) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int indexOfSong = songList.indexOf(songEntity);
                    if(indexOfSong < songList.size() - 1) {
                        songList.remove(songEntity);
                        songList.add(indexOfSong + 1, songEntity);
                        notifyDataSetChanged();
                    }
                }
            };
        }

        public View.OnClickListener onDeleteClicked(SongEntity songEntity) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(source.equalsIgnoreCase("playList")) {
                        songList.remove(songEntity);
                        notifyDataSetChanged();
                    } else if (source.equalsIgnoreCase("allSongsList")) {
                        callback.deleteClicked(songEntity);
                    }
                }
            };
        }
    }

    public List<SongEntity> getSongList() {
        return songList;
    }

    public interface Callback {
        void onChosenSongCellClicked(SongEntity songEntity);
        void onAllSongCellClicked(SongEntity songEntity);
        void onCellLongClicked();
        void deleteClicked(SongEntity songEntity);
    }
}
