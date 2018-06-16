package com.systers.conference.ui.online.login;

import android.support.annotation.NonNull;

import com.systers.conference.ui.base.BasePresenter;

/**
 * Created by haroon on 6/11/18.
 */

public class LoginPresenter extends BasePresenter<LogInMvpView> {

    @Override
    public void attachView(LogInMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void performLogIn(@NonNull String userName, @NonNull String password) {
        checkViewAttached();
        //TODO: Make the network call here.
        //TODO: Call the getMvpView().logInSuccessful() or getMvpView().logInFailed() implemented in LoginActivity here.
    }
}
