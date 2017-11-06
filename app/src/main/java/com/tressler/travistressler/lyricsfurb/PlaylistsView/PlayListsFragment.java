package com.tressler.travistressler.lyricsfurb.PlaylistsView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tressler.travistressler.lyricsfurb.Application.di.LyricsApplication;
import com.tressler.travistressler.lyricsfurb.CreatePlayListView.CreatePlayListFragment;
import com.tressler.travistressler.lyricsfurb.MainView.MainActivity;
import com.tressler.travistressler.lyricsfurb.R;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by travistressler on 11/2/17.
 */

public class PlayListsFragment extends Fragment implements PlayListsView {

    @Inject protected PlayListsPresenter presenter;

    @OnClick(R.id.button_create_play_list)
    protected void onCreatePlayListClicked(View view) {
        presenter.createPlayListClicked();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_lists, container, false);
        ButterKnife.bind(this, view);
        ((LyricsApplication)getActivity().getApplication()).getComponent().inject(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    public static PlayListsFragment newInstance() {

        Bundle args = new Bundle();

        PlayListsFragment fragment = new PlayListsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void launchPlaylistCreator() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder_playlists, CreatePlayListFragment.newInstance()).commit();
    }

    @Override
    public void takeUserToAllSongsFragment() {
        ((MainActivity)getActivity()).transitionToAllSongsFragment();
    }

    @Override
    public void toastInstructions() {
        Toast.makeText(getContext(), "Please Add a Song First!", Toast.LENGTH_SHORT).show();
    }
}
