package com.tressler.travistressler.lyricspal.Application.di;

import com.tressler.travistressler.lyricspal.AddSongView.AddSongFragment;
import com.tressler.travistressler.lyricspal.AddToPlaylistView.AddToPlaylistFragment;
import com.tressler.travistressler.lyricspal.AllSongsView.AllSongsFragment;
import com.tressler.travistressler.lyricspal.CreatePlayListView.CreatePlayListFragment;
import com.tressler.travistressler.lyricspal.LyricsView.LyricsFragment;
import com.tressler.travistressler.lyricspal.MainView.MainActivity;
import com.tressler.travistressler.lyricspal.PlayListView.PlayListFragment;
import com.tressler.travistressler.lyricspal.PlaylistsView.PlayListsFragment;
import com.tressler.travistressler.lyricspal.SingleLyricsView.SingleLyricsFragment;
import com.tressler.travistressler.lyricspal.SplashView.SplashActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by travistressler on 11/2/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);

    void inject(PlayListsFragment playListsFragment);

    void inject(AllSongsFragment allSongsFragment);

    void inject(AddSongFragment addSongFragment);

    void inject(PlayListFragment playListFragment);

    void inject(CreatePlayListFragment createPlayListFragment);

    void inject(AddToPlaylistFragment addToPlaylistFragment);

    void inject(LyricsFragment lyricsFragment);

    void inject(SingleLyricsFragment singleLyricsFragment);
}
