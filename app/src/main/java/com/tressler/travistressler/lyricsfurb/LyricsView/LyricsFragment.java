package com.tressler.travistressler.lyricsfurb.LyricsView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tressler.travistressler.lyricsfurb.Application.di.LyricsApplication;
import com.tressler.travistressler.lyricsfurb.R;
import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongEntity;
import com.tressler.travistressler.lyricsfurb.Util.MainPagerAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by travistressler on 11/7/17.
 */

public class LyricsFragment extends Fragment implements LyricsView {

    @Inject LyricsPresenter presenter;
    @BindView(R.id.main_view_pager)
    protected ViewPager pager;

    @BindView(R.id.lyrics_play_list_title)
    protected TextView playListTitle;

    private MainPagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lyrics, container, false);
        ButterKnife.bind(this, view);
        ((LyricsApplication) getActivity().getApplication()).getComponent().inject(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
        String playlistName = getArguments().getString("PLAYLIST");
        int position = getArguments().getInt("POSITION");
        presenter.songsRetrieved(playlistName, position);
        pagerAdapter = new MainPagerAdapter();
        pager.setAdapter(pagerAdapter);

    }

    public static LyricsFragment newInstance() {

        Bundle args = new Bundle();

        LyricsFragment fragment = new LyricsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showLyricsForPlaylist(List<SongEntity> lyricsList, int position) {
        for(int i = lyricsList.size() - 1; i >= 0; i--) {
            LayoutInflater layoutInflater = getActivity().getLayoutInflater();
            FrameLayout v0 = (FrameLayout) layoutInflater.inflate (R.layout.view_lyrics, null);
            TextView textView = (TextView) v0.findViewById(R.id.lyrics_text);
            textView.setText(lyricsList.get(i).getSongLyrics());
            TextView textTitle = (TextView) v0.findViewById(R.id.lyrics_title);
            textTitle.setText(lyricsList.get(i).getSongTitle());
            pagerAdapter.addView(v0, 0);
        }
        pagerAdapter.notifyDataSetChanged();
        pager.setCurrentItem(position);
    }

    @Override
    public void showPlaylistTitle(String playListName) {
        playListTitle.setText(playListName);
    }
}
