package com.tressler.travistressler.lyricspal.Application.di;

import android.app.Application;

/**
 * Created by travistressler on 11/2/17.
 */

public class LyricsApplication extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }


    public ApplicationComponent getComponent() {
        return component;
    }
}
