package com.tressler.travistressler.lyricspal.SingleLyricsView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tressler.travistressler.lyricspal.Application.di.LyricsApplication;
import com.tressler.travistressler.lyricspal.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by travistressler on 11/8/17.
 */

public class SingleLyricsFragment extends Fragment implements SingleLyricsView {

    @BindView(R.id.single_lyrics_title)
    protected TextView singleLyricsTitle;

    @BindView(R.id.single_lyrics_text)
    protected TextView singleLyricsText;

    @Inject
    SingleLyricsPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_lyrics, container, false);
        ButterKnife.bind(this, view);
        ((LyricsApplication)getActivity().getApplication()).getComponent().inject(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
        String songTitle = getArguments().getString("SONG");
        String songLyrics = getArguments().getString("LYRICS");
        presenter.songInformationRetrieved(songTitle, songLyrics);
    }

    public static SingleLyricsFragment newInstance() {

        Bundle args = new Bundle();

        SingleLyricsFragment fragment = new SingleLyricsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showSong(String songTitle, String songLyrics) {
        singleLyricsTitle.setText(songTitle);
        singleLyricsText.setText(songLyrics);
    }
}
