package com.tressler.travistressler.lyricsfurb.MainView;

import javax.inject.Inject;

/**
 * Created by travistressler on 11/2/17.
 */

public class MainPresenter {

    private MainView view;

    @Inject
    public MainPresenter() {

    }

    public void attachView(MainView view) {
        this.view = view;
    }
}
