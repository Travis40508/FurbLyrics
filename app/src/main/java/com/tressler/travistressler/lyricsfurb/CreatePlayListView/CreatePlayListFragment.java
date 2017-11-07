package com.tressler.travistressler.lyricsfurb.CreatePlayListView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.tressler.travistressler.lyricsfurb.Application.di.LyricsApplication;
import com.tressler.travistressler.lyricsfurb.R;
import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongEntity;
import com.tressler.travistressler.lyricsfurb.Util.SongListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by travistressler on 11/2/17.
 */

public class CreatePlayListFragment extends Fragment implements CreatePlayListView, SongListAdapter.Callback {

    @Inject protected CreatePlayListPresenter presenter;

    @BindView(R.id.recycler_view_all_songs)
    protected RecyclerView recyclerViewAllSongs;

    @BindView(R.id.recycler_view_chosen_songs)
    protected RecyclerView recyclerViewChosenSongs;

    @BindView(R.id.button_save_play_list)
    protected Button savePlayListButton;

    @OnClick(R.id.button_save_play_list)
    protected void onSavePlayListButtonClicked(View view) {
        presenter.savePlayListButtonClicked();
    }

    @OnTextChanged(R.id.input_play_list_name)
    protected void onPlayListNameTextChanged(CharSequence playListName) {
        presenter.playListNameTextChanged(playListName);
    }

    private SongListAdapter allSongsAdapter;
    private SongListAdapter chosenSongsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_play_list, container, false);
        ButterKnife.bind(this, view);
        ((LyricsApplication)getActivity().getApplication()).getComponent().inject(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);

    }

    public static CreatePlayListFragment newInstance() {

        Bundle args = new Bundle();

        CreatePlayListFragment fragment = new CreatePlayListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showSongList(List<SongEntity> songEntities) {
        allSongsAdapter = new SongListAdapter(songEntities, this, "allSongsCreatePlaylist");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewAllSongs.setAdapter(allSongsAdapter);
        recyclerViewAllSongs.setLayoutManager(linearLayoutManager);
        allSongsAdapter.notifyDataSetChanged();
    }

    @Override
    public void setupChosenSongs() {
        List<SongEntity> songEntitiesList = new ArrayList<>();
        chosenSongsAdapter = new SongListAdapter(songEntitiesList, this, "chosenSongsCreatePlaylist");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewChosenSongs.setAdapter(chosenSongsAdapter);
        recyclerViewChosenSongs.setLayoutManager(linearLayoutManager);
        chosenSongsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSavePlaylistButton() {
        savePlayListButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSavePlaylistButton() {
        savePlayListButton.setVisibility(View.GONE);
    }

    @Override
    public void detachFragment() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .remove(getActivity().getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_holder_playlists)).commit();
    }


    @Override
    public void onChosenSongCellClicked(SongEntity songEntity) {
        presenter.chosenSongListCellClicked(songEntity);
        allSongsAdapter.addSong(songEntity);
    }

    @Override
    public void onAllSongCellClicked(SongEntity songEntity) {
        presenter.allSongListCellClicked(songEntity);
        chosenSongsAdapter.addSong(songEntity);
    }

    @Override
    public void onCellLongClicked() {

    }

    @Override
    public void deleteClicked(SongEntity songEntity) {

    }
}
