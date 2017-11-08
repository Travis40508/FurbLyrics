package com.tressler.travistressler.lyricspal.Repository.lyricsdatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by travistressler on 11/4/17.
 */

@Dao
public interface SongDao {

    @Insert(onConflict = REPLACE)
    void insertSongEntity(SongEntity songEntity);

    @Query("SELECT * FROM SongEntity")
    Flowable<List<SongEntity>> getSongs();

    @Query("SELECT * FROM SongEntity")
    List<SongEntity> getSongsStatic();


    @Query("SELECT * FROM SongEntity WHERE songTitle LIKE :title")
    SongEntity getChosenSong(String title);


    @Update
    void updateSongEntity(SongEntity songEntity);

    @Delete
    void deleteSongEntity(SongEntity songEntity);
}
