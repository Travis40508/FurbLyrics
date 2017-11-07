package com.tressler.travistressler.lyricsfurb.AddToPlaylistView;

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
 * Created by travistressler on 11/6/17.
 */

public class AddToPlaylistPresenter {

    private final SongDatabase songDatabase;
    private final Scheduler workerThread;
    private AddToPlaylistView view;
    private String playListTitle;
    private List<SongEntity> songList;
    private List<SongEntity> songsToBeSaved;
    private List<SongEntity> songsToBeDeleted;

    @Inject
    public AddToPlaylistPresenter(SongDatabase songDatabase, Scheduler workerThread) {
        this.songDatabase = songDatabase;
        this.workerThread = workerThread;
    }

    public void attachView(AddToPlaylistView view) {
        this.view = view;
    }

    public void getLists() {
        songList = new ArrayList<>();
        songsToBeSaved = new ArrayList<>();
        songsToBeDeleted = new ArrayList<>();
        workerThread.createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                List<String> songsInPlaylist = songDatabase.playlistDao().getChosenPlaylist(playListTitle).getSongsInPlaylist();
                for (String item : songsInPlaylist) {
                    SongEntity songEntity = songDatabase.songDao().getChosenSong(item);
                    songList.add(songEntity);
                }
                List<SongEntity> staticSongList = songDatabase.songDao().getSongsStatic();
                for(SongEntity song : songList) {
                    for(SongEntity songEntity : staticSongList) {
                        if(songEntity.getSongTitle().equalsIgnoreCase(song.getSongTitle())) {
                            songsToBeDeleted.add(songEntity);
                        }
                    }
                }
                staticSongList.removeAll(songsToBeDeleted);
                songsToBeSaved.addAll(songList);
                view.displayOtherListSongs(staticSongList);
            }
        });

        view.displayPlaylistSongs(songList);
    }

    public void retrievePlaylistTitle(String playlistTitle) {
        this.playListTitle = playlistTitle;
        view.setTitleText(playListTitle);
    }

    public void chosenSongCellClicked(SongEntity songEntity) {
        songsToBeSaved.remove(songEntity);

    }

    public void allSongCellClicked(SongEntity songEntity) {
        songsToBeSaved.add(songEntity);
    }

    public void saveNewPlaylist() {
        workerThread.createWorker().schedule(new Runnable() {
            @Override
            public void run() {
                List<String> newSongs = new ArrayList<>();
                PlaylistEntity playlistEntity = songDatabase.playlistDao().getChosenPlaylist(playListTitle);
                for(SongEntity song : songsToBeSaved) {
                    newSongs.add(song.getSongTitle());
                }
                playlistEntity.setSongsInPlaylist(newSongs);
                songDatabase.playlistDao().updatePlaylist(playlistEntity);
            }
        });
        view.toastSuccess();
        view.detachFragment();
    }
}
