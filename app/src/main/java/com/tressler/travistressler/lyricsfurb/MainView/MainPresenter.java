package com.tressler.travistressler.lyricsfurb.MainView;

import android.util.Log;

import com.tressler.travistressler.lyricsfurb.Repository.lyricsapi.LyricsApi;

import javax.inject.Inject;

/**
 * Created by travistressler on 11/2/17.
 */

public class MainPresenter {

    private final LyricsApi lyricsApi;
    private MainView view;

    @Inject
    public MainPresenter(LyricsApi lyricsApi) {
        this.lyricsApi = lyricsApi;
    }

    public void attachView(MainView view) {
        this.view = view;
        if(this.view != null) {
            view.populateFragmentList();
            testLogic();
        }
    }

    private void testLogic() {
        lyricsApi.getSong("Gentle Waves", "Falling From Grace").subscribe(song -> {
           Log.d("@@@@", song.getLyrics());
        }, throwable -> {
            Log.d("@@@@", "invalid song...");
        });
    }

    //TODO make header for all songs list with ADD SONG button which will take user to fragment that will allow them to add a song. Also add Rx support for
    //TODO Room and make sure database returns flowables
}
