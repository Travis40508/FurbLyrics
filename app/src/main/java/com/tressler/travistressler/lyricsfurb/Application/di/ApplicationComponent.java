package com.tressler.travistressler.lyricsfurb.Application.di;

import com.tressler.travistressler.lyricsfurb.MainView.MainActivity;
import com.tressler.travistressler.lyricsfurb.SplashView.SplashActivity;

import dagger.Component;

/**
 * Created by travistressler on 11/2/17.
 */

@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);
}
