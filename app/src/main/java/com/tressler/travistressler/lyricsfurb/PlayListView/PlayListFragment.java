package com.tressler.travistressler.lyricsfurb.PlayListView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tressler.travistressler.lyricsfurb.Application.di.LyricsApplication;
import com.tressler.travistressler.lyricsfurb.R;
import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongEntity;
import com.tressler.travistressler.lyricsfurb.Util.SongListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by travistressler on 11/2/17.
 */

public class PlayListFragment extends Fragment implements PlayListView, SongListAdapter.Callback {

    @Inject protected PlayListPresenter presenter;

    private SongListAdapter adapter;

    @BindView(R.id.recycler_view_playlist)
    protected RecyclerView recyclerViewPlaylist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_list, container, false);
        ButterKnife.bind(this, view);
        ((LyricsApplication)getActivity().getApplication()).getComponent().inject(this);
        List<String> songsInPlaylist = getArguments().getStringArrayList("SONGS");
        presenter.songsRetrieved(songsInPlaylist);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    public static PlayListFragment newInstance() {

        Bundle args = new Bundle();

        PlayListFragment fragment = new PlayListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showPlaylistSongs(List<SongEntity> playlistSongs) {
        adapter = new SongListAdapter(playlistSongs, this, "playlist");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewPlaylist.setAdapter(adapter);
        recyclerViewPlaylist.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onChosenSongCellClicked(SongEntity songEntity) {

    }

    @Override
    public void onAllSongCellClicked(SongEntity songEntity) {

    }
}
