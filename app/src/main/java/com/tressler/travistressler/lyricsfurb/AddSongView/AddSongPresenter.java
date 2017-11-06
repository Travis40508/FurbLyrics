package com.tressler.travistressler.lyricsfurb.AddSongView;

import android.util.Log;

import com.tressler.travistressler.lyricsfurb.Repository.lyricsapi.LyricsApi;
import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongDatabase;
import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongEntity;

import javax.inject.Inject;

import io.reactivex.Scheduler;

/**
 * Created by travistressler on 11/2/17.
 */

public class AddSongPresenter {

    private final LyricsApi lyricsApi;
    private final SongDatabase songDatabase;
    private final Scheduler workerThread;
    private AddSongView view;
    private String artistName;
    private String songTitle;

    @Inject
    public AddSongPresenter(LyricsApi lyricsApi, SongDatabase songDatabase, Scheduler workerThread) {
        this.lyricsApi = lyricsApi;
        this.songDatabase = songDatabase;
        this.workerThread = workerThread;
    }

    public void attachView(AddSongView view) {
        this.view = view;
    }

    public void artistNameTextChanged(CharSequence artistName) {
        this.artistName = artistName.toString();
        checkArtistAndTitle();
    }

    private void checkArtistAndTitle() {
        if((artistName != null && artistName.length() > 0) && (songTitle != null && songTitle.length() > 0)) {
            view.showSaveSongButton();
        } else {
            view.hideShowSaveSongButton();
        }
    }

    public void songTitleTextChanged(CharSequence songTitle) {
        this.songTitle = songTitle.toString();
        checkArtistAndTitle();
    }

    public void saveSongButtonClicked() {
        view.showProgressBar();
        lyricsApi.getSong(artistName, songTitle).subscribe(song -> {
            workerThread.createWorker().schedule(new Runnable() {
                @Override
                public void run() {
                    SongEntity newSong = new SongEntity(songTitle, artistName, song.getLyrics());
                    songDatabase.songDao().insertSongEntity(newSong);
                }
            });
            view.hideProgressBar();
            view.showSongAddedToast();
            view.eraseArtistNameText();
            view.eraseSongTitleText();
        }, throwable -> {
            throwable.printStackTrace();
            view.hideProgressBar();
            view.showSongNotFoundToast();
        });
    }
}
