package com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> playLists;

    public SongEntity(String songTitle, String songArtist, String songLyrics) {
        this.songTitle = songTitle;
        this.songArtist = songArtist;
        this.songLyrics = songLyrics;
        this.playLists = new ArrayList<>();
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

    public List<String> getPlayLists() {
        return playLists;
    }

    public void setPlayLists(List<String> playLists) {
        this.playLists = playLists;
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

    public void addToPlayLists(String playList) {
        playLists.add(playList);
    }
}
