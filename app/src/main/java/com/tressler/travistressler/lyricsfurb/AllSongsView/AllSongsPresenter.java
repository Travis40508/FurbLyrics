package com.tressler.travistressler.lyricsfurb.AllSongsView;

import javax.inject.Inject;

/**
 * Created by travistressler on 11/2/17.
 */

public class AllSongsPresenter {

    private AllSongsView view;

    @Inject
    public AllSongsPresenter() {
    }

    public void attachView(AllSongsView view) {
        this.view = view;
    }
}
