package com.tressler.travistressler.lyricspal.SplashView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by travistressler on 11/2/17.
 */

public class SplashPresenter {

    private SplashView view;

    @Inject
    public SplashPresenter() {

    }

    public void attachView(SplashView view) {
        this.view = view;
        if(view != null) {
            startCountDown();
        }
    }

    private void startCountDown() {
        Observable.interval(2, TimeUnit.SECONDS)
                .take(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(finished -> {
                   view.startMainActivity();
                });
    }
}
