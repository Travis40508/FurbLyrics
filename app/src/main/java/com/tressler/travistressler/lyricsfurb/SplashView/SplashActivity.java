package com.tressler.travistressler.lyricsfurb.SplashView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tressler.travistressler.lyricsfurb.R;

public class SplashActivity extends AppCompatActivity implements SplashView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
}
