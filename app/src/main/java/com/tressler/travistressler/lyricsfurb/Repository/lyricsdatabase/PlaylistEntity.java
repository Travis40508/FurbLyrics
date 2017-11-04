package com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by travistressler on 11/4/17.
 */

@Entity
public class PlaylistEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String playListName;

    public PlaylistEntity(String playListName) {
        this.playListName = playListName;
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
}
