package com.tressler.travistressler.lyricsfurb.AddSongView;

import javax.inject.Inject;

/**
 * Created by travistressler on 11/2/17.
 */

public class AddSongPresenter {

    private AddSongView view;

    @Inject
    public AddSongPresenter() {
    }

    public void attachView(AddSongView view) {
        this.view = view;
    }
}
