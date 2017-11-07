package com.tressler.travistressler.lyricsfurb.PlayListView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tressler.travistressler.lyricsfurb.AddToPlaylistView.AddToPlaylistFragment;
import com.tressler.travistressler.lyricsfurb.Application.di.LyricsApplication;
import com.tressler.travistressler.lyricsfurb.R;
import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.SongEntity;
import com.tressler.travistressler.lyricsfurb.Util.SongListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by travistressler on 11/2/17.
 */

public class PlayListFragment extends Fragment implements PlayListView, SongListAdapter.Callback {

    @Inject
    protected PlayListPresenter presenter;

    private SongListAdapter adapter;

    @BindView(R.id.recycler_view_playlist)
    protected RecyclerView recyclerViewPlaylist;

    @BindView(R.id.button_done)
    protected Button doneButton;

    @BindView(R.id.button_add_to_play_list)
    protected Button addToPlaylist;

    @BindView(R.id.play_list_title)
    protected TextView playlistTitle;

    @BindView(R.id.button_cancel)
    protected Button cancelButton;

    @OnClick(R.id.button_cancel)
    protected void onCancelButtonClicked(View view) {
        presenter.cancelClicked();
    }

    @OnClick(R.id.button_done)
    protected void onDoneButtonClicked(View view) {
        presenter.doneClicked(adapter.getSongList());
    }

    @OnClick(R.id.button_add_to_play_list)
    protected void onAddToPlaylistButtonClicked(View view) {
        presenter.addToPlaylistButtonClicked();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_list, container, false);
        ButterKnife.bind(this, view);
        ((LyricsApplication) getActivity().getApplication()).getComponent().inject(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<String> songsInPlaylist = getArguments().getStringArrayList("SONGS");
        String playListName = getArguments().getString("PLAYLIST");
        presenter.songsRetrieved(songsInPlaylist);
        presenter.playListTitleRetrieved(playListName);
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
    public void showDoneButton() {
        doneButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCancelButton() {
        cancelButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCancelButton() {
        cancelButton.setVisibility(View.GONE);
    }

    @Override
    public void hideDoneButton() {
        doneButton.setVisibility(View.GONE);
    }

    @Override
    public void hideExtraOptions() {
        adapter.hideExtraOptions();
    }

    @Override
    public void showSuccessToast() {
        Toast.makeText(getContext(), "List Successfully Updated!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setTitle(String playListName) {
        playlistTitle.setText(playListName);
    }

    @Override
    public void hideAddSongButton() {
        addToPlaylist.setVisibility(View.GONE);
    }

    @Override
    public void showAddSongButton() {
        addToPlaylist.setVisibility(View.VISIBLE);
    }

    @Override
    public void launchAddSongToPlaylistFragment(Bundle bundle) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder_playlist, AddToPlaylistFragment.newInstance()).commit();
    }


    @Override
    public void onChosenSongCellClicked(SongEntity songEntity) {

    }

    @Override
    public void onAllSongCellClicked(SongEntity songEntity) {

    }

    @Override
    public void onCellLongClicked() {
        presenter.cellLongClicked();
    }

    @Override
    public void deleteClicked(SongEntity songEntity) {

    }
}
