package com.tressler.travistressler.lyricsfurb.PlayListView;

import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongEntity;

import java.util.List;

/**
 * Created by travistressler on 11/2/17.
 */

public interface PlayListView {
    void showPlaylistSongs(List<SongEntity> playlistSongs);

    void showDoneButton();

    void showCancelButton();

    void hideCancelButton();

    void hideDoneButton();

    void hideExtraOptions();

    void showSuccessToast();

}
