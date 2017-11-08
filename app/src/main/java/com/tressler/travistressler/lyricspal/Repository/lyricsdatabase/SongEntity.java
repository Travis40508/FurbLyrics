package com.tressler.travistressler.lyricspal.Repository.lyricsdatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by travistressler on 11/4/17.
 */

@Entity
public class SongEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String songTitle;
    private String songArtist;
    private String songLyrics;
    private boolean editingEnabled;
    private boolean deletingEnabled;

    public SongEntity(String songTitle, String songArtist, String songLyrics) {
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.songLyrics = songLyrics;
        this.editingEnabled = false;
        this.deletingEnabled = false;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSongLyrics() {
        return songLyrics;
    }

    public void setSongLyrics(String songLyrics) {
        this.songLyrics = songLyrics;
    }

    public boolean isEditingEnabled() {
        return editingEnabled;
    }

    public void setEditingEnabled(boolean editingEnabled) {
        this.editingEnabled = editingEnabled;
    }

    public boolean isDeletingEnabled() {
        return deletingEnabled;
    }

    public void setDeletingEnabled(boolean deletingEnabled) {
        this.deletingEnabled = deletingEnabled;
    }
}
