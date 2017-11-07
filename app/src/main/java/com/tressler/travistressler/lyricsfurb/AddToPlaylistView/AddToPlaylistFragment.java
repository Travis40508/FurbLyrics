package com.tressler.travistressler.lyricsfurb.AddToPlaylistView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tressler.travistressler.lyricsfurb.Application.di.LyricsApplication;
import com.tressler.travistressler.lyricsfurb.R;
import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongEntity;
import com.tressler.travistressler.lyricsfurb.Util.SongListAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by travistressler on 11/6/17.
 */

public class AddToPlaylistFragment extends Fragment implements AddToPlaylistView, SongListAdapter.Callback {

    @Inject AddToPlaylistPresenter presenter;

    @BindView(R.id.text_play_list_title)
    protected TextView playListTitle;

    @BindView(R.id.recycler_view_add_to_play_list_play_all_songs)
    protected RecyclerView recyclerViewAllSongs;

    @BindView(R.id.recycler_view_add_to_play_list_play_list_songs)
    protected RecyclerView recyclerViewPlaylistSongs;

    private SongListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_to_playlist, container, false);
        ButterKnife.bind(this, view);
        ((LyricsApplication)getActivity().getApplication()).getComponent().inject(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    public static AddToPlaylistFragment newInstance() {

        Bundle args = new Bundle();

        AddToPlaylistFragment fragment = new AddToPlaylistFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        String playlistTitle = getArguments().getString("PLAYLIST");
        presenter.retrievePlaylistTitle(playlistTitle);
    }

    @Override
    public void onChosenSongCellClicked(SongEntity songEntity) {

    }

    @Override
    public void onAllSongCellClicked(SongEntity songEntity) {

    }

    @Override
    public void onCellLongClicked() {

    }

    @Override
    public void deleteClicked(SongEntity songEntity) {

    }

    @Override
    public void setTitleText(String playListTitleText) {
        playListTitle.setText(playListTitleText);
    }
}
