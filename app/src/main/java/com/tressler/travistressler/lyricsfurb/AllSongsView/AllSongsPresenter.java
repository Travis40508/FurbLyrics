package com.tressler.travistressler.lyricsfurb.AllSongsView;

import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.PlaylistEntity;
import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongDatabase;
import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongEntity;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by travistressler on 11/2/17.
 */

public class AllSongsPresenter {

    private final SongDatabase songDatabase;
    private final Scheduler workerThread;
    private AllSongsView view;

    @Inject
    public AllSongsPresenter(SongDatabase songDatabase, Scheduler workerThread) {
        this.songDatabase = songDatabase;
        this.workerThread = workerThread;
    }

    public void attachView(AllSongsView view) {
        this.view = view;
        if(view != null) {
            retrieveSongs();
        }
    }

    private void retrieveSongs() {
        workerThread.createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                songDatabase.songDao().getSongs()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(songEntities -> {
                           view.showListOfSongs(songEntities);
                        }, throwable -> {
                            view.showErrorLoadingToast();
                        });
            }
        });
    }

    public void addSongClicked() {
        view.launchAddSongFragment();
    }

    public void cellLongClicked() {
        view.showCancelButton();
    }

    public void cancelButtonClicked() {
        view.hideCancelButton();
        view.hideDeleteIconInList();
    }

    public void deleteClicked(SongEntity songEntity) {
        view.showAlertDialog(songEntity);
    }

    public void confirmDeleteClicked(SongEntity songEntity) {
        workerThread.createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                songDatabase.songDao().deleteSongEntity(songEntity);
                songDatabase.playlistDao().getPlaylists()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(playlistEntities -> {
                            for(PlaylistEntity playlistEntity : playlistEntities) {
                                if(playlistEntity.getSongsInPlaylist().contains(songEntity.getSongTitle())) {
                                    playlistEntity.removeFromPlaylist(songEntity.getSongTitle());
                                    workerThread.createWorker().schedule(new Runnable() {
                                        @Override
                                        public void run() {
                                            songDatabase.playlistDao().updatePlaylist(playlistEntity);
                                        }
                                    });

                                }
                            }
                        });
            }
        });
        view.hideCancelButton();
        view.toastDeleteSuccessful();
    }

}
