package com.tressler.travistressler.lyricsfurb.PlayListView;

import javax.inject.Inject;

/**
 * Created by travistressler on 11/2/17.
 */

public class PlayListPresenter {

    private PlayListView view;

    @Inject
    public PlayListPresenter() {
    }

    public void attachView(PlayListView view) {
        this.view = view;
    }
}
