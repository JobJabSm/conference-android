package com.systers.conference.ui.online.profile.edit;

import com.systers.conference.ui.base.BasePresenter;

/**
 * Created by haroon on 6/11/18.
 */

public class EditProfilePresenter extends BasePresenter<EditProfileMvpView> {

    @Override
    public void attachView(EditProfileMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void updateProfile() {
        checkViewAttached();
        getMvpView().showProgressDialog();
        //TODO: Make the retrofit request here
    }
}
