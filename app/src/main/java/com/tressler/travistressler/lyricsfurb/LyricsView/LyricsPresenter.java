package com.tressler.travistressler.lyricsfurb.LyricsView;

import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongDatabase;
import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by travistressler on 11/7/17.
 */

public class LyricsPresenter {

    private final SongDatabase songDatabase;
    private final Scheduler workerThread;
    private LyricsView view;

    @Inject
    public LyricsPresenter(SongDatabase songDatabase, Scheduler workerThread) {
        this.songDatabase = songDatabase;
        this.workerThread = workerThread;
    }

    public void attachView(LyricsView view) {
        this.view = view;
    }

    public void songsRetrieved(String playListName, int position) {
        List<SongEntity> songsList = new ArrayList<>();
        workerThread.createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                List<String> playListSongs = songDatabase.playlistDao().getChosenPlaylist(playListName).getSongsInPlaylist();
                for(String item : playListSongs) {
                    SongEntity songEntity = songDatabase.songDao().getChosenSong(item);
                    songsList.add(songEntity);
                }
                AndroidSchedulers.mainThread().createWorker().schedule(new Runnable() {
                    @Override
                    public void run() {
                        view.showLyricsForPlaylist(songsList, position);
                        view.showPlaylistTitle(playListName);
                    }
                });
            }
        });
    }
}
