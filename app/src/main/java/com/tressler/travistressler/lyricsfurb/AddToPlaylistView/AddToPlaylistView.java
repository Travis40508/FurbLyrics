package com.tressler.travistressler.lyricsfurb.AddToPlaylistView;

import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongEntity;

import java.util.List;

/**
 * Created by travistressler on 11/6/17.
 */

public interface AddToPlaylistView {
    void setTitleText(String playListTitle);

    void displayPlaylistSongs(List<SongEntity> songList);

    void displayOtherListSongs(List<SongEntity> otherSongList);

    void toastSuccess();

    void detachFragment(List<String> newSongs);
}
