package com.tressler.travistressler.lyricsfurb.CreatePlayListView;

import javax.inject.Inject;

/**
 * Created by travistressler on 11/2/17.
 */

public class CreatePlayListPresenter {

    private CreatePlayListView view;

    @Inject
    public CreatePlayListPresenter() {
    }

    public void attachView(CreatePlayListView view) {
        this.view = view;
    }
}
