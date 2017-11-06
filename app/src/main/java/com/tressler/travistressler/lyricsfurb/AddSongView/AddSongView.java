package com.tressler.travistressler.lyricsfurb.AddSongView;

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
}
