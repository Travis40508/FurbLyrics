package com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

/**
 * Created by travistressler on 11/4/17.
 */

@Entity
public class PlaylistEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String playListName;
    private List<String> songsInPlaylist;
    private boolean deletingEnabled;

    public PlaylistEntity(String playListName, List<String> songsInPlaylist) {
        this.playListName = playListName;
        this.songsInPlaylist = songsInPlaylist;
        deletingEnabled = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayListName() {
        return playListName;
    }

    public void setPlayListName(String playListName) {
        this.playListName = playListName;
    }

    public List<String> getSongsInPlaylist() {
        return songsInPlaylist;
    }

    public void setSongsInPlaylist(List<String> songsInPlaylist) {
        this.songsInPlaylist = songsInPlaylist;
    }

    public void addToPlaylist(String songtitle) {
        songsInPlaylist.add(songtitle);
    }

    public void removeFromPlaylist(String songTitle) {
        songsInPlaylist.remove(songTitle);
    }

    public boolean isDeletingEnabled() {
        return deletingEnabled;
    }

    public void setDeletingEnabled(boolean deletingEnabled) {
        this.deletingEnabled = deletingEnabled;
    }
}
