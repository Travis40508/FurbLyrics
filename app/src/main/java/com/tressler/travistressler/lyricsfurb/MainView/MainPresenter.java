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
        }
    }


    public void onBackPressed(boolean fragmentIsNull, boolean createPlaylistFragmentIsNull, boolean addToPlaylistFragmentIsNull) {
        if(fragmentIsNull && createPlaylistFragmentIsNull) {
            view.closeApp();
        } else if (!fragmentIsNull){
            view.removeFragment();
        } else if (!addToPlaylistFragmentIsNull) {
            view.removeAddToPlaylistFragment();
        } else if (!createPlaylistFragmentIsNull) {
          view.removeCreatePlayListFragment();
        } else {
            view.closeApp();
        }
    }

}
