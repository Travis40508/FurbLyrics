package com.tressler.travistressler.lyricsfurb.PlaylistsView;

import javax.inject.Inject;

/**
 * Created by travistressler on 11/2/17.
 */

public class PlayListsPresenter {

    private PlayListsView view;

    @Inject
    public PlayListsPresenter() {
    }

    public void attachView(PlayListsView view) {
        this.view = view;
    }
}
