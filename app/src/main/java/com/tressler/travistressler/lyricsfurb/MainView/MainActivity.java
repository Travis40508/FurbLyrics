package com.tressler.travistressler.lyricsfurb.MainView;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tressler.travistressler.lyricsfurb.AllSongsView.AllSongsFragment;
import com.tressler.travistressler.lyricsfurb.Application.di.LyricsApplication;
import com.tressler.travistressler.lyricsfurb.PlaylistsView.PlayListsFragment;
import com.tressler.travistressler.lyricsfurb.R;
import com.tressler.travistressler.lyricsfurb.Util.MainViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.view_pager)
    protected ViewPager viewPager;

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
        presenter.attachView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter = new MainViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
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

    @Override
    public void removeFragment() {
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.fragment_holder)).commit();
    }

    @Override
    public void removeCreatePlayListFragment() {
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.fragment_holder_playlists)).commit();
    }

    @Override
    public void removeAddToPlaylistFragment() {
        getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.fragment_holder_playlist)).commit();
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
}
