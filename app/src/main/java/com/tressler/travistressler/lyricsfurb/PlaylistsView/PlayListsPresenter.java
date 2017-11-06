package com.tressler.travistressler.lyricsfurb.PlaylistsView;

import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongDatabase;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by travistressler on 11/2/17.
 */

public class PlayListsPresenter {

    private final SongDatabase songDatabase;
    private final Scheduler workerThread;
    private PlayListsView view;
    private int listSize = 0;

    @Inject
    public PlayListsPresenter(SongDatabase songDatabase, Scheduler workerThread) {
        this.songDatabase = songDatabase;
        this.workerThread = workerThread;
    }

    public void attachView(PlayListsView view) {
        this.view = view;
        if(view != null) {
            listenForSongListChanges();
        }
    }

    private void listenForSongListChanges() {
        workerThread.createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                songDatabase.songDao().getSongs()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(songEntities -> {
                            listSize = songEntities.size();
                        });
            }
        });
    }

    public void createPlayListClicked() {
        if(listSize > 0) {
            view.launchPlaylistCreator();
        } else {
            view.takeUserToAllSongsFragment();
            view.toastInstructions();
        }
    }
}
