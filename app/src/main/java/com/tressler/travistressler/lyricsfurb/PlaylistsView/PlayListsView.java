package com.tressler.travistressler.lyricsfurb.PlaylistsView;

import android.os.Bundle;

import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.PlaylistEntity;

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
}
