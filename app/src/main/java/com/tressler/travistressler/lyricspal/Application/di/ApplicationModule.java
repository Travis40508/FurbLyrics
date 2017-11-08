package com.tressler.travistressler.lyricspal.Application.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.tressler.travistressler.lyricspal.Repository.lyricsapi.LyricsApi;
import com.tressler.travistressler.lyricspal.Repository.lyricsapi.LyricsApiImpl;
import com.tressler.travistressler.lyricspal.Repository.lyricsapi.LyricsRetrofit;
import com.tressler.travistressler.lyricspal.Repository.lyricsdatabase.SongDatabase;
import com.tressler.travistressler.lyricspal.Repository.lyricsdatabase.SongDatabaseImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by travistressler on 11/2/17.
 */

@Module
public class ApplicationModule {

    private final Application application;
    private String baseUrl = "https://api.lyrics.ovh/v1/";

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


    @Singleton
    @Provides
    public Retrofit providesRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    @Singleton
    @Provides
    public LyricsRetrofit providesLyricsRetrofit(Retrofit retrofit) {
        LyricsRetrofit lyricsRetrofit = retrofit.create(LyricsRetrofit.class);
        return lyricsRetrofit;
    }

    @Singleton
    @Provides
    public LyricsApi providesLyricsApi(LyricsRetrofit lyricsRetrofit) {
        return new LyricsApiImpl(lyricsRetrofit);
    }

    @Singleton
    @Provides
    public Scheduler providesWorkerThread() {
        return Schedulers.io();
    }
}
