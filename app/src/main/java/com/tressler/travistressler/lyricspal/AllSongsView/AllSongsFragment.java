package com.tressler.travistressler.lyricspal.AllSongsView;

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

import com.tressler.travistressler.lyricspal.AddSongView.AddSongFragment;
import com.tressler.travistressler.lyricspal.Application.di.LyricsApplication;
import com.tressler.travistressler.lyricspal.MainView.MainActivity;
import com.tressler.travistressler.lyricspal.R;
import com.tressler.travistressler.lyricspal.Repository.lyricsdatabase.SongEntity;
import com.tressler.travistressler.lyricspal.SingleLyricsView.SingleLyricsFragment;
import com.tressler.travistressler.lyricspal.Util.SongListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by travistressler on 11/2/17.
 */

public class AllSongsFragment extends Fragment implements AllSongsView, SongListAdapter.Callback {

    @BindView(R.id.recycler_view_songs)
    protected RecyclerView recyclerView;

    @BindView(R.id.button_cancel)
    protected Button cancelButton;

    private SongListAdapter adapter;

    @Inject protected AllSongsPresenter presenter;

    @OnClick(R.id.button_add_song)
    protected void addSongButton(View view) {
        presenter.addSongClicked();
    }

    @OnClick(R.id.button_cancel)
    protected void onCancelButtonClicked(View view) {
        presenter.cancelButtonClicked();
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
    }

    @Override
    public void onResume() {
        super.onResume();
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
        ((MainActivity)getActivity()).stopScrolling();
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.fragment_holder, AddSongFragment.newInstance()).commit();
    }



    @Override
    public void showErrorLoadingToast() {
        Toast.makeText(getContext(), "Error Displaying Songs", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showListOfSongs(List<SongEntity> songEntities) {
        adapter = new SongListAdapter(songEntities, this, "allSongsList");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();
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
    public void hideDeleteIconInList() {
        adapter.hideExtraOptions();
    }

    @Override
    public void showAlertDialog(SongEntity songEntity) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle("Delete Song")
                .setMessage("Are you sure you want to delete this song?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.confirmDeleteClicked(songEntity);
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
    public void toastDeleteSuccessful() {
        Toast.makeText(getContext(), "Song Successfully Deleted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void launchSingleLyricsFragment(Bundle bundle) {
        SingleLyricsFragment singleLyricsFragment = SingleLyricsFragment.newInstance();
        singleLyricsFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.fragment_holder, singleLyricsFragment).commit();
        ((MainActivity)getActivity()).stopScrolling();
    }


    @Override
    public void onChosenSongCellClicked(SongEntity songEntity) {
        //Implemented with Adapter interface, in case schema ever changes.
    }

    @Override
    public void onAllSongCellClicked(SongEntity songEntity) {
        presenter.cellClicked(songEntity);
    }

    @Override
    public void onCellLongClicked() {
        presenter.cellLongClicked();
    }

    @Override
    public void deleteClicked(SongEntity songEntity) {
        presenter.deleteClicked(songEntity);
    }
}
