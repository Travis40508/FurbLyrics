package com.tressler.travistressler.lyricsfurb.PlayListView;

import android.util.Log;

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

    public void playListNameRetrieved(String playlistName) {
        workerThread.createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                songDatabase.songDao().getSongs()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(songEntities -> {
                            for(SongEntity song : songEntities) {
                                if(song.getPlayLists().contains(playlistName)) {
                                    playlistSongs.add(song);
                                }
                            }
                            view.showPlaylistSongs(playlistSongs);
                        });
            }
        });
    }
}
