package com.tressler.travistressler.lyricsfurb.AllSongsView;

import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongEntity;

import java.util.List;

/**
 * Created by travistressler on 11/2/17.
 */

public interface AllSongsView {
    void launchAddSongFragment();


    void showErrorLoadingToast();

    void showListOfSongs(List<SongEntity> songEntities);

    void showCancelButton();

    void hideCancelButton();

    void hideDeleteIconInList();

    void showAlertDialog(SongEntity songEntity);

    void toastDeleteSuccessful();
}
