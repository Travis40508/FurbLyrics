package com.tressler.travistressler.lyricspal.AddSongView;

import com.tressler.travistressler.lyricspal.Repository.lyricsdatabase.PlaylistEntity;

import java.util.List;

/**
 * Created by travistressler on 11/2/17.
 */

public interface AddSongView {
    void showSaveSongButton();

    void hideShowSaveSongButton();

    void showProgressBar();

    void hideProgressBar();

    void showSongNotFoundToast();

    void showSongAddedToast();

    void eraseArtistNameText();

    void eraseSongTitleText();

    void loadSpinner(List<PlaylistEntity> playlistEntities);
}
