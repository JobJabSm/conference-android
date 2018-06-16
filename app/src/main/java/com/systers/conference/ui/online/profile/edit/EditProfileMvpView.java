package com.systers.conference.ui.online.profile.edit;

import android.support.annotation.Nullable;

import com.systers.conference.ui.base.MvpView;

/**
 * Created by haroon on 6/11/18.
 */

public interface EditProfileMvpView extends MvpView {

    void loadProfile();

    void profileUpdateSuccessful();

    void profileUpdateFailed(@Nullable String errorMessage);

    void showProgressDialog();

    void hideProgressDialog();
}
