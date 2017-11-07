package com.tressler.travistressler.lyricsfurb.AddToPlaylistView;

import javax.inject.Inject;

/**
 * Created by travistressler on 11/6/17.
 */

public class AddToPlaylistPresenter {

    private AddToPlaylistView view;
    private String playListTitle;

    @Inject
    public AddToPlaylistPresenter() {
    }

    public void attachView(AddToPlaylistView view) {
        this.view = view;
    }

    public void retrievePlaylistTitle(String playlistTitle) {
        this.playListTitle = playlistTitle;
        view.setTitleText(playListTitle);
    }
}
