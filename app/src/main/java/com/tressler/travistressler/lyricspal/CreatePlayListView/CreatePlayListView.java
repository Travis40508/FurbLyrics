package com.tressler.travistressler.lyricspal.CreatePlayListView;

import com.tressler.travistressler.lyricspal.Repository.lyricsdatabase.SongEntity;

import java.util.List;

/**
 * Created by travistressler on 11/2/17.
 */

public interface CreatePlayListView {
    void showSongList(List<SongEntity> songEntities);

    void setupChosenSongs();

    void showSavePlaylistButton();

    void hideSavePlaylistButton();

    void detachFragment();

    void toastPlaylistNameWarning();

    void toastPlaylistAlreadyExists();
}
