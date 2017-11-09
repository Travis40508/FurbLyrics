package com.tressler.travistressler.lyricspal.PlaylistsView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
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

import com.tressler.travistressler.lyricspal.Application.di.LyricsApplication;
import com.tressler.travistressler.lyricspal.CreatePlayListView.CreatePlayListFragment;
import com.tressler.travistressler.lyricspal.MainView.MainActivity;
import com.tressler.travistressler.lyricspal.PlayListView.PlayListFragment;
import com.tressler.travistressler.lyricspal.R;
import com.tressler.travistressler.lyricspal.Repository.lyricsdatabase.PlaylistEntity;
import com.tressler.travistressler.lyricspal.Util.PlaylistsAdapter;

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

    @BindView(R.id.button_create_play_list)
    protected Button createPlaylistButton;

    @BindView(R.id.button_delete_play_list_done)
    protected Button deletePlaylistDoneButton;

    @OnClick(R.id.button_delete_play_list_done)
    protected void onDeletePlaylistDoneClick(View view) {
        presenter.doneClicked();
    }

    @OnClick(R.id.button_create_play_list)
    protected void onCreatePlayListClicked(View view) {
        presenter.createPlayListClicked();
        ((MainActivity)getActivity()).stopScrolling();
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
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.fragment_holder_playlists, CreatePlayListFragment.newInstance()).commit();
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
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.fragment_holder_playlists, playListFragment).commit();
        ((MainActivity)getActivity()).stopScrolling();
    }

    @Override
    public void hideDeleteButtons() {
        adapter.hideDeleteButtons();
    }

    @Override
    public void hideDoneButton() {
        deletePlaylistDoneButton.setVisibility(View.GONE);
    }

    @Override
    public void showCreateButton() {
        createPlaylistButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDeleteButtons() {
        adapter.showDeleteButtons();
    }

    @Override
    public void showDoneButton() {
        deletePlaylistDoneButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCreateButton() {
        createPlaylistButton.setVisibility(View.GONE);
    }

    @Override
    public void showAlertDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle("Delete Setlist")
                .setMessage("Are you sure you want to delete this setlist?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.confirmDeleteClicked();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void toastDeleteSuccess() {
        Toast.makeText(getContext(), "Setlist Successfully Deleted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPlaylistClicked(PlaylistEntity playlistEntity) {
        presenter.playListClicked(playlistEntity);
    }

    @Override
    public void onCellLongClicked() {
        presenter.onCellLongClicked();
    }

    @Override
    public void deleteClicked(PlaylistEntity playlistEntity) {
        presenter.deleteClicked(playlistEntity);
    }
}
