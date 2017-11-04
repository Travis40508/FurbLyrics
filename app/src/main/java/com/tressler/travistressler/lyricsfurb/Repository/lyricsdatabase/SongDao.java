package com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by travistressler on 11/4/17.
 */

@Dao
public interface SongDao {

    @Insert(onConflict = REPLACE)
    void insertSongEntity(SongEntity songEntity);

    @Query("SELECT * FROM SongEntity")
    List<SongEntity> getSongs();
}
