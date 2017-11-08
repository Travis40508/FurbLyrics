package com.tressler.travistressler.lyricspal.PlaylistsView;

import android.os.Bundle;

import com.tressler.travistressler.lyricspal.Repository.lyricsdatabase.PlaylistEntity;
import com.tressler.travistressler.lyricspal.Repository.lyricsdatabase.SongDatabase;

import java.util.ArrayList;
import java.util.List;

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
    private PlaylistEntity playListToBeDeleted;

    @Inject
    public PlayListsPresenter(SongDatabase songDatabase, Scheduler workerThread) {
        this.songDatabase = songDatabase;
        this.workerThread = workerThread;
    }

    public void attachView(PlayListsView view) {
        this.view = view;
        if(view != null) {
            retrievePlaylists();
            listenForSongListChanges();
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
                            view.showPlaylists(playlistEntities);
                        }, throwable -> {
                            view.showErrorLoadingListToast();
                        });
            }
        });
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

    public void playListClicked(PlaylistEntity playlistEntity) {
        List<String> songsInPlaylist = new ArrayList<>();
        songsInPlaylist = playlistEntity.getSongsInPlaylist();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("SONGS", (ArrayList<String>) songsInPlaylist);
        bundle.putString("PLAYLIST", playlistEntity.getPlayListName());
        view.launchPlaylistFragment(bundle);
    }

    public void doneClicked() {
        view.hideDeleteButtons();
        view.hideDoneButton();
        view.showCreateButton();
    }

    public void onCellLongClicked() {
        view.showDeleteButtons();
        view.showDoneButton();
        view.hideCreateButton();
    }

    public void deleteClicked(PlaylistEntity playlistEntity) {
        this.playListToBeDeleted = playlistEntity;
        view.showAlertDialog();
    }

    public void confirmDeleteClicked() {
        workerThread.createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                songDatabase.playlistDao().deletePlaylist(playListToBeDeleted);
            }
        });
        view.toastDeleteSuccess();
        view.showCreateButton();
        view.hideDoneButton();
    }
}
