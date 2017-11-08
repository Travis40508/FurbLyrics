package com.tressler.travistressler.lyricspal.PlayListView;

import android.os.Bundle;

import com.tressler.travistressler.lyricspal.Repository.lyricsdatabase.PlaylistEntity;
import com.tressler.travistressler.lyricspal.Repository.lyricsdatabase.SongDatabase;
import com.tressler.travistressler.lyricspal.Repository.lyricsdatabase.SongEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by travistressler on 11/2/17.
 */

public class PlayListPresenter {

    private final SongDatabase songDatabase;
    private final Scheduler workerThread;
    private PlayListView view;
    private List<SongEntity> playlistSongs;
    private String playListName;
    private List<SongEntity> songList;
    private List<String> songsInPlaylist;

    @Inject
    public PlayListPresenter(SongDatabase songDatabase, Scheduler workerThread) {
        this.songDatabase = songDatabase;
        this.workerThread = workerThread;
        playlistSongs = new ArrayList<>();
        songList = new ArrayList<>();
    }

    public void attachView(PlayListView view) {
        this.view = view;
        if(view != null) {

        }
    }

    public void onResume() {
        songList.clear();
        if(songsInPlaylist != null) {
            songsInPlaylist.clear();
        }
        workerThread.createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                songsInPlaylist = songDatabase.playlistDao().getChosenPlaylist(playListName).getSongsInPlaylist();
                for(String item : songsInPlaylist) {
                    SongEntity songEntity = songDatabase.songDao().getChosenSong(item);
                    songList.add(songEntity);
                }
                AndroidSchedulers.mainThread().createWorker().schedule(new Runnable() {
                    @Override
                    public void run() {
                        view.showPlaylistSongs(songList);
                    }
                });
            }
        });
    }


    //TODO splash screen

    public void cellLongClicked() {
        view.hideAddSongButton();
        view.showDoneButton();
        view.showCancelButton();
    }

    public void cancelClicked() {
        songList.clear();
        view.hideCancelButton();
        view.hideDoneButton();
        view.hideExtraOptions();

        workerThread.createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                songsInPlaylist = songDatabase.playlistDao().getChosenPlaylist(playListName).getSongsInPlaylist();
                for(String item : songsInPlaylist) {
                    SongEntity songEntity = songDatabase.songDao().getChosenSong(item);
                    songList.add(songEntity);
                }
            }
        });
        view.showPlaylistSongs(songList);
        view.showAddSongButton();
    }

    public void doneClicked(List<SongEntity> songList) {
        List<String> songTitles = new ArrayList<>();
        workerThread.createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                PlaylistEntity playlistEntity = songDatabase.playlistDao().getChosenPlaylist(playListName);
                for(SongEntity song : songList) {
                    songTitles.add(song.getSongTitle());
                }
                playlistEntity.setSongsInPlaylist(songTitles);
                songDatabase.playlistDao().updatePlaylist(playlistEntity);
            }
        });

        view.hideCancelButton();
        view.hideDoneButton();
        view.showSuccessToast();
        view.hideExtraOptions();
        view.showAddSongButton();
    }

    public void playListTitleRetrieved(String playListName) {
        this.playListName = playListName;
        view.setTitle(playListName);
    }

    public void addToPlaylistButtonClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("PLAYLIST", playListName);
        view.launchAddSongToPlaylistFragment(bundle);
    }

    public void cellClicked(SongEntity songEntity) {
        workerThread.createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                List<String> songsInPlaylist = songDatabase.playlistDao().getChosenPlaylist(playListName).getSongsInPlaylist();
                int position = songsInPlaylist.indexOf(songEntity.getSongTitle());
                Bundle bundle = new Bundle();
                bundle.putString("PLAYLIST", playListName);
                bundle.putInt("POSITION", position);

                AndroidSchedulers.mainThread().createWorker().schedule(new Runnable() {
                    @Override
                    public void run() {
                        view.showLyricsForPlaylist(bundle);
                    }
                });
            }
        });


    }
}
