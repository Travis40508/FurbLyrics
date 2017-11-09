package com.tressler.travistressler.lyricspal.SplashView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tressler.travistressler.lyricspal.MainView.MainActivity;
import com.tressler.travistressler.lyricspal.R;
import com.tressler.travistressler.lyricspal.Application.di.LyricsApplication;

import javax.inject.Inject;

public class SplashActivity extends AppCompatActivity implements SplashView {

    @Inject protected SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ((LyricsApplication)getApplication()).getComponent().inject(this);
        presenter.attachView(this);
    }

    @Override
    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        finish();
    }

}
