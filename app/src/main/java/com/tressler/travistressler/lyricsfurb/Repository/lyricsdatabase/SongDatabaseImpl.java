package com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

/**
 * Created by travistressler on 11/4/17.
 */

@Database(version = 4, entities = {SongEntity.class, PlaylistEntity.class})
@TypeConverters({Converters.class})

public abstract class SongDatabaseImpl extends RoomDatabase implements SongDatabase{

    @Override
    public abstract SongDao songDao();

    @Override
    public abstract PlaylistDao playlistDao();
}
