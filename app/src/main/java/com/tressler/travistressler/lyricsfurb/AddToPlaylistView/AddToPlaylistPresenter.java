package com.tressler.travistressler.lyricsfurb.AddToPlaylistView;

import javax.inject.Inject;

/**
 * Created by travistressler on 11/6/17.
 */

public class AddToPlaylistPresenter {

    private AddToPlaylistView view;

    @Inject
    public AddToPlaylistPresenter() {
    }

    public void attachView(AddToPlaylistView view) {
        this.view = view;
    }
}
