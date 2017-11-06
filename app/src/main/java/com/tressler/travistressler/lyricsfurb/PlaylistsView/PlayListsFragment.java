package com.tressler.travistressler.lyricsfurb.PlaylistsView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tressler.travistressler.lyricsfurb.Application.di.LyricsApplication;
import com.tressler.travistressler.lyricsfurb.CreatePlayListView.CreatePlayListFragment;
import com.tressler.travistressler.lyricsfurb.MainView.MainActivity;
import com.tressler.travistressler.lyricsfurb.PlayListView.PlayListFragment;
import com.tressler.travistressler.lyricsfurb.R;
import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.PlaylistEntity;
import com.tressler.travistressler.lyricsfurb.Util.PlaylistsAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by travistressler on 11/2/17.
 */

public class PlayListsFragment extends Fragment implements PlayListsView, PlaylistsAdapter.Callback {

    @Inject protected PlayListsPresenter presenter;

    @BindView(R.id.recycler_view_play_lists)
    protected RecyclerView recyclerViewPlaylists;

    @OnClick(R.id.button_create_play_list)
    protected void onCreatePlayListClicked(View view) {
        presenter.createPlayListClicked();
    }

    private PlaylistsAdapter adapter;

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

    @Override
    public void showPlaylists(List<PlaylistEntity> playlistEntities) {
        adapter = new PlaylistsAdapter(playlistEntities, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewPlaylists.setAdapter(adapter);
        recyclerViewPlaylists.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorLoadingListToast() {
        Toast.makeText(getContext(), "Error Loading Playlists", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void launchPlaylistFragment(Bundle bundle) {
        PlayListFragment playListFragment = PlayListFragment.newInstance();
        playListFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder_playlists, playListFragment).commit();
    }

    @Override
    public void onPlaylistClicked(PlaylistEntity playlistEntity) {
        presenter.playListClicked(playlistEntity.getPlayListName());
    }
}
