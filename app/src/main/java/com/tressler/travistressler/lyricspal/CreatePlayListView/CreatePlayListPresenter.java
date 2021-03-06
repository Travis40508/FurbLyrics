package com.tressler.travistressler.lyricspal.CreatePlayListView;

import com.tressler.travistressler.lyricspal.Repository.lyricsdatabase.PlaylistEntity;
import com.tressler.travistressler.lyricspal.Repository.lyricsdatabase.SongDatabase;
import com.tressler.travistressler.lyricspal.Repository.lyricsdatabase.SongEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by travistressler on 11/2/17.
 */

public class CreatePlayListPresenter {

    private final SongDatabase songDatabase;
    private final Scheduler workerThread;
    private CreatePlayListView view;
    private List<SongEntity> songsToBeSaved;
    private String playListName;

    @Inject
    public CreatePlayListPresenter(SongDatabase songDatabase, Scheduler workerThread) {
        this.songDatabase = songDatabase;
        this.workerThread = workerThread;
        songsToBeSaved = new ArrayList<>();
    }

    public void attachView(CreatePlayListView view) {
        this.view = view;
        if (view != null) {
            retrieveAllSongs();
            view.setupChosenSongs();
        }
    }

    private void retrieveAllSongs() {
        workerThread.createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                songDatabase.songDao().getSongs()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(songEntities -> {
                            view.showSongList(songEntities);
                        });
            }
        });
    }


    public void chosenSongListCellClicked(SongEntity songEntity) {
        songsToBeSaved.remove(songEntity);
        if (songsToBeSaved.size() > 0) {
            view.showSavePlaylistButton();
        } else {
            view.hideSavePlaylistButton();
        }
    }

    public void allSongListCellClicked(SongEntity songEntity) {
        songsToBeSaved.add(songEntity);
        if (songsToBeSaved.size() > 0) {
            view.showSavePlaylistButton();
        } else {
            view.hideSavePlaylistButton();
        }
    }

    public void savePlayListButtonClicked() {
        workerThread.createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                List<String> songsInPlaylist = new ArrayList<>();
                for (SongEntity song : songsToBeSaved) {
                    songsInPlaylist.add(song.getSongTitle());
                }
                if (playListName != null && playListName.length() > 0 && songDatabase.playlistDao().getChosenPlaylist(playListName) == null) {
                    songDatabase.playlistDao().insertPlaylist(new PlaylistEntity(playListName.toUpperCase(), songsInPlaylist));
                    AndroidSchedulers.mainThread().createWorker().schedule(new Runnable() {
                        @Override
                        public void run() {
                            view.detachFragment();
                        }
                    });
                } else if (playListName != null && playListName.length() > 0 && songDatabase.playlistDao().getChosenPlaylist(playListName).getPlayListName().equalsIgnoreCase(playListName)) {
                    AndroidSchedulers.mainThread().createWorker().schedule(new Runnable() {
                        @Override
                        public void run() {
                            view.toastPlaylistAlreadyExists();
                        }
                    });
                } else {
                    AndroidSchedulers.mainThread().createWorker().schedule(new Runnable() {
                        @Override
                        public void run() {
                            view.toastPlaylistNameWarning();
                        }
                    });

                }

            }
        });
    }

    public void playListNameTextChanged(CharSequence playListName) {
        this.playListName = playListName.toString();
    }
}
