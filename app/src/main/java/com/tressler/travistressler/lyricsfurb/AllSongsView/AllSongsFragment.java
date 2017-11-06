package com.tressler.travistressler.lyricsfurb.AllSongsView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tressler.travistressler.lyricsfurb.AddSongView.AddSongFragment;
import com.tressler.travistressler.lyricsfurb.Application.di.LyricsApplication;
import com.tressler.travistressler.lyricsfurb.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by travistressler on 11/2/17.
 */

public class AllSongsFragment extends Fragment implements AllSongsView {

    @Inject protected AllSongsPresenter presenter;

    @OnClick(R.id.button_add_song)
    protected void addSongButton(View view) {
        presenter.addSongClicked();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_songs, container, false);
        ButterKnife.bind(this, view);
        ((LyricsApplication)getActivity().getApplication()).getComponent().inject(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    public static AllSongsFragment newInstance() {

        Bundle args = new Bundle();

        AllSongsFragment fragment = new AllSongsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void launchAddSongFragment() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, AddSongFragment.newInstance()).commit();
    }
}
