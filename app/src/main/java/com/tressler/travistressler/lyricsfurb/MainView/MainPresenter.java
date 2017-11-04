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
//            testLogic();
        }
    }

//    private void testLogic() {
//        lyricsApi.getSong("Cad8a7sd7asd987as9d789asd", "Cad87a9sd7sa87d89as7d").subscribe(song -> {
//           Log.d("@@@@", song.getLyrics());
//        }, throwable -> {
//            Log.d("@@@@", "invalid song...");
//        });
//    }
}
