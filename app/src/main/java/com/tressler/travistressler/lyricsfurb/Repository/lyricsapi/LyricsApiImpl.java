package com.tressler.travistressler.lyricsfurb.Repository.lyricsapi;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by travistressler on 11/4/17.
 */

public class LyricsApiImpl implements LyricsApi {


    private final LyricsRetrofit lyricsRetrofit;

    public LyricsApiImpl(LyricsRetrofit lyricsRetrofit) {
        this.lyricsRetrofit = lyricsRetrofit;
    }

    @Override
    public Observable<LyricsRetrofit.Song> getSong(String artistName, String songTitle) {
        return lyricsRetrofit.getSong(artistName, songTitle)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
