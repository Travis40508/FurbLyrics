package com.tressler.travistressler.lyricsfurb.CreatePlayListView;

import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongEntity;

import java.util.List;

/**
 * Created by travistressler on 11/2/17.
 */

public interface CreatePlayListView {
    void showSongList(List<SongEntity> songEntities);

    void setupChosenSongs();

    void showSavePlaylistButton();

    void hideSavePlaylistButton();
}
