package com.tressler.travistressler.lyricsfurb.AddSongView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.tressler.travistressler.lyricsfurb.Application.di.LyricsApplication;
import com.tressler.travistressler.lyricsfurb.R;
import com.tressler.travistressler.lyricsfurb.Repository.lyricsdatabase.PlaylistEntity;

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

public class AddSongFragment extends Fragment implements AddSongView {

    @BindView(R.id.spinner_play_lists)
    protected Spinner playListSpinner;

    @BindView(R.id.button_save_song)
    protected Button saveSongButton;

    @BindView(R.id.progress_bar_add_song)
    protected ProgressBar progressBarAddSong;

    @BindView(R.id.input_song_title)
    protected EditText songTitleInput;

    @BindView(R.id.input_artist_name)
    protected EditText artistNameInput;

    private ArrayAdapter<String> adapter;

    @Inject protected AddSongPresenter presenter;

    @OnClick(R.id.button_save_song)
    protected void onSaveSongButtonClicked(View view) {
        presenter.saveSongButtonClicked();
    }

    @OnTextChanged(R.id.input_artist_name)
    public void onArtistNameTextChange(CharSequence charSequence) {
        presenter.artistNameTextChanged(charSequence);
    }

    @OnTextChanged(R.id.input_song_title)
    public void onSongTitleTextChange(CharSequence charSequence) {
        presenter.songTitleTextChanged(charSequence);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_song, container, false);
        ButterKnife.bind(this, view);
        ((LyricsApplication)getActivity().getApplication()).getComponent().inject(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    public static AddSongFragment newInstance() {

        Bundle args = new Bundle();

        AddSongFragment fragment = new AddSongFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showSaveSongButton() {
        saveSongButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideShowSaveSongButton() {
        saveSongButton.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        progressBarAddSong.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBarAddSong.setVisibility(View.GONE);
    }

    @Override
    public void showSongNotFoundToast() {
        Toast.makeText(getContext(), "Song Not Found. Please Try Again.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSongAddedToast() {
        Toast.makeText(getContext(), "Song Successfully Saved!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void eraseArtistNameText() {
        artistNameInput.setText("");
        artistNameInput.requestFocus();
    }

    @Override
    public void eraseSongTitleText() {
        songTitleInput.setText("");
    }

    @Override
    public void loadSpinner(List<PlaylistEntity> playlistEntities) {
        List<String> playLists = new ArrayList<>();
        for(PlaylistEntity playList : playlistEntities) {
            playLists.add(playList.getPlayListName());
        }
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, playLists);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playListSpinner.setAdapter(adapter);
        playListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                presenter.playListSelected((String) adapterView.getItemAtPosition(pos));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
