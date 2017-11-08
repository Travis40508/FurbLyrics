package com.tressler.travistressler.lyricspal.SingleLyricsView;

import javax.inject.Inject;

/**
 * Created by travistressler on 11/8/17.
 */

public class SingleLyricsPresenter {
    private SingleLyricsView view;


    @Inject
    public SingleLyricsPresenter() {
    }

    public void attachView(SingleLyricsView view) {
        this.view = view;
    }

    public void songInformationRetrieved(String songTitle, String songLyrics) {
        view.showSong(songTitle, songLyrics);
    }
}
