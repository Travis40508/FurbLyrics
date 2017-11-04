package com.tressler.travistressler.lyricsfurb.Application.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongDatabase;
import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongDatabaseImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by travistressler on 11/2/17.
 */

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public Context providesApplication() {
        return application;
    }

    @Singleton
    @Provides
    public SongDatabase providesSongDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), SongDatabaseImpl.class, "song_database").build();
    }
}
