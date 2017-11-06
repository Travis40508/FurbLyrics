package com.tressler.travistressler.lyricsfurb.AllSongsView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tressler.travistressler.lyricsfurb.AddSongView.AddSongFragment;
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

public class AllSongsFragment extends Fragment implements AllSongsView {

    @BindView(R.id.recycler_view_songs)
    protected RecyclerView recyclerView;

    private SongListAdapter adapter;

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
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, AddSongFragment.newInstance()).commit();
    }



    @Override
    public void showErrorLoadingToast() {
        Toast.makeText(getContext(), "Error Displaying Songs", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showListOfSongs(List<SongEntity> songEntities) {
        adapter = new SongListAdapter(songEntities);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();
    }
}
