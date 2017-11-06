package com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

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

    @Update
    void updateSongEntity(SongEntity songEntity);
}
