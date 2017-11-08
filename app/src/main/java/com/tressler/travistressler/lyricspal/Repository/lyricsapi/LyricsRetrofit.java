package com.tressler.travistressler.lyricspal.Repository.lyricsapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by travistressler on 11/4/17.
 */

public interface LyricsRetrofit {
    @GET("{artist}/{title}")
    Observable<Song> getSong(@Path("artist") String artist, @Path("title") String title);


    public class Song {
        @Expose @SerializedName("lyrics")
        private String lyrics;

        public String getLyrics() {
            return lyrics;
        }
    }
}
