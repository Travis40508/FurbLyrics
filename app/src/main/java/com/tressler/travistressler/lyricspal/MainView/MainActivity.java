package com.tressler.travistressler.lyricspal.MainView;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tressler.travistressler.lyricspal.AllSongsView.AllSongsFragment;
import com.tressler.travistressler.lyricspal.Application.di.LyricsApplication;
import com.tressler.travistressler.lyricspal.PlaylistsView.PlayListsFragment;
import com.tressler.travistressler.lyricspal.R;
import com.tressler.travistressler.lyricspal.Util.CustomViewPager;
import com.tressler.travistressler.lyricspal.Util.MainViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.view_pager)
    protected CustomViewPager viewPager;

    @Inject
    protected MainPresenter presenter;

    private MainViewPagerAdapter adapter;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((LyricsApplication)getApplication()).getComponent().inject(this);
        adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        presenter.attachView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPager.setSwipeable(true);
    }

    @Override
    public void populateFragmentList() {
        fragmentList.add(PlayListsFragment.newInstance());
        fragmentList.add(AllSongsFragment.newInstance());
    }

    @Override
    public void closeApp() {
        super.onBackPressed();
    }

    public void stopScrolling() {
        viewPager.setSwipeable(false);
    }

    @Override
    public void removeFragment() {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).remove(getSupportFragmentManager().findFragmentById(R.id.fragment_holder)).commit();
        viewPager.setSwipeable(true);
    }

    @Override
    public void removeCreatePlayListFragment() {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).remove(getSupportFragmentManager().findFragmentById(R.id.fragment_holder_playlists)).commit();
        viewPager.setSwipeable(true);
    }

    @Override
    public void removeAddToPlaylistFragment() {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right).remove(getSupportFragmentManager().findFragmentById(R.id.fragment_holder_playlist)).commit();
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed(getSupportFragmentManager().findFragmentById(R.id.fragment_holder) == null,
                getSupportFragmentManager().findFragmentById(R.id.fragment_holder_playlists) == null,
                getSupportFragmentManager().findFragmentById(R.id.fragment_holder_playlist) == null);
    }

    public void transitionToAllSongsFragment() {
        viewPager.setCurrentItem(1);
    }

    //Starts scrolling back up
    public void startScrolling() {
        viewPager.setSwipeable(true);
    }
}
