package com.tressler.travistressler.lyricspal.AddSongView;

import com.tressler.travistressler.lyricspal.Repository.lyricsapi.LyricsApi;
import com.tressler.travistressler.lyricspal.Repository.lyricsdatabase.PlaylistEntity;
import com.tressler.travistressler.lyricspal.Repository.lyricsdatabase.SongDatabase;
import com.tressler.travistressler.lyricspal.Repository.lyricsdatabase.SongEntity;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    private String playListSelected;

    @Inject
    public AddSongPresenter(LyricsApi lyricsApi, SongDatabase songDatabase, Scheduler workerThread) {
        this.lyricsApi = lyricsApi;
        this.songDatabase = songDatabase;
        this.workerThread = workerThread;
    }

    public void attachView(AddSongView view) {
        this.view = view;
        if(view != null) {
            retrievePlaylists();
        }
    }

    private void retrievePlaylists() {
        workerThread.createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                songDatabase.playlistDao().getPlaylists()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(playlistEntities -> {
                            view.loadSpinner(playlistEntities);
                        });
            }
        });
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
                    PlaylistEntity playlistEntity = songDatabase.playlistDao().getChosenPlaylist(playListSelected);
                    SongEntity newSong = new SongEntity(songTitle.toUpperCase(), artistName.toUpperCase(), song.getLyrics());
                    songDatabase.songDao().insertSongEntity(newSong);
                    if(playlistEntity != null) {
                        playlistEntity.addToPlaylist(newSong.getSongTitle());
                        songDatabase.playlistDao().updatePlaylist(playlistEntity);
                    }
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

    public void playListSelected(String itemAtPosition) {
        this.playListSelected = itemAtPosition;
    }
}
