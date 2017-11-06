package com.tressler.travistressler.lyricsfurb.PlayListView;

import android.util.Log;

import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.PlaylistEntity;
import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongDatabase;
import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by travistressler on 11/2/17.
 */

public class PlayListPresenter {

    private final SongDatabase songDatabase;
    private final Scheduler workerThread;
    private PlayListView view;
    private List<SongEntity> playlistSongs;
    private String playListName;

    @Inject
    public PlayListPresenter(SongDatabase songDatabase, Scheduler workerThread) {
        this.songDatabase = songDatabase;
        this.workerThread = workerThread;
        playlistSongs = new ArrayList<>();
    }

    public void attachView(PlayListView view) {
        this.view = view;
        if(view != null) {

        }
    }

    public void songsRetrieved(List<String> songsInPlaylist) {
        List<SongEntity> songList = new ArrayList<>();
        workerThread.createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                for(String item : songsInPlaylist) {
                    SongEntity songEntity = songDatabase.songDao().getChosenSong(item);
                    songList.add(songEntity);
                }
                view.showPlaylistSongs(songList);
            }
        });
    }

    public void cellLongClicked() {
        view.showDoneButton();
        view.showCancelButton();
    }

    public void cancelClicked() {
        view.hideCancelButton();
        view.hideDoneButton();
        view.hideExtraOptions();
    }

    public void doneClicked(List<SongEntity> songList) {
        workerThread.createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                List<String> songTitles = new ArrayList<>();
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
    }

    public void playListTitleRetrieved(String playListName) {
        this.playListName = playListName;
    }
}
