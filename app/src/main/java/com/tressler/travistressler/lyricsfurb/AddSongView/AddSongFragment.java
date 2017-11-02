package com.tressler.travistressler.lyricsfurb.AddSongView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tressler.travistressler.lyricsfurb.Application.di.LyricsApplication;
import com.tressler.travistressler.lyricsfurb.R;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by travistressler on 11/2/17.
 */

public class AddSongFragment extends Fragment implements AddSongView {

    @Inject protected AddSongPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_song, container, false);
        ButterKnife.bind(this, view);
        ((LyricsApplication)getActivity().getApplication()).getComponent().inject(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    public static AddSongFragment newInstance() {

        Bundle args = new Bundle();

        AddSongFragment fragment = new AddSongFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
