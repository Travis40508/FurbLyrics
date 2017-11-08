package com.tressler.travistressler.lyricspal.Repository.lyricsapi;

import io.reactivex.Observable;

/**
 * Created by travistressler on 11/4/17.
 */

public interface LyricsApi {

    Observable<LyricsRetrofit.Song> getSong(String artistName, String songTitle);
}
