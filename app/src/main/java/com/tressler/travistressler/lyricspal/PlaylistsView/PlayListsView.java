package com.tressler.travistressler.lyricspal.PlaylistsView;

import android.os.Bundle;

import com.tressler.travistressler.lyricspal.Repository.lyricsdatabase.PlaylistEntity;

import java.util.List;

/**
 * Created by travistressler on 11/2/17.
 */

public interface PlayListsView {
    void launchPlaylistCreator();

    void takeUserToAllSongsFragment();

    void toastInstructions();

    void showPlaylists(List<PlaylistEntity> playlistEntities);

    void showErrorLoadingListToast();

    void launchPlaylistFragment(Bundle bundle);

    void hideDeleteButtons();

    void hideDoneButton();

    void showCreateButton();

    void showDeleteButtons();

    void showDoneButton();

    void hideCreateButton();

    void showAlertDialog();

    void toastDeleteSuccess();
}
