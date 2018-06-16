package com.systers.conference.ui.online.login;

import android.support.annotation.Nullable;

import com.systers.conference.ui.base.MvpView;

/**
 * Created by haroon on 6/11/18.
 */

public interface LogInMvpView extends MvpView {

    void logInSuccessful();

    void logInFailed(@Nullable String errorMessage);

    void showProgressDialog();

    void hideProgressDialog();
}
