package com.systers.conference.ui.online.profile.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.systers.conference.R;
import com.systers.conference.ui.online.profile.edit.EditProfileActivity;
import com.systers.conference.utils.AccountUtils;
import com.systers.conference.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements ViewProfileMvpView {

    private static String LOG_TAG = LogUtils.makeLogTag(ProfileFragment.class);
    @BindView(R.id.avatar)
    CircleImageView mAvatar;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.subhead)
    TextView mSubHead;
    private Unbinder mUnbidden;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mUnbidden = ButterKnife.bind(this, view);

        loadProfile();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbidden.unbind();
    }

    @OnClick(R.id.edit_profile)
    public void startEditProfileActivity() {
        startActivity(new Intent(getActivity(), EditProfileActivity.class));
    }

    @Override
    public void loadProfile() {
        if (AccountUtils.getProfilePictureUrl(getActivity()) != null) {
            LogUtils.LOGE(LOG_TAG, AccountUtils.getProfilePictureUrl(getActivity()));
            Picasso.with(getActivity()).load(Uri.parse(AccountUtils.getProfilePictureUrl(getActivity())))
                    .resize(getResources().getInteger(R.integer.avatar_dimen), getResources().getInteger(R.integer.avatar_dimen))
                    .placeholder(R.drawable.male_icon_9_glasses)
                    .error(R.drawable.male_icon_9_glasses)
                    .centerCrop()
                    .into(mAvatar);
        } else {
            mAvatar.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.male_icon_9_glasses));
        }
        mName.setText(AccountUtils.getFirstName(getActivity()) + " " + AccountUtils.getLastName(getActivity()));
        if (!TextUtils.isEmpty(AccountUtils.getCompanyRole(getActivity()))) {
            mSubHead.setText(AccountUtils.getCompanyRole(getActivity()));
        }
        if (!TextUtils.isEmpty(AccountUtils.getCompanyName(getActivity()))) {
            String text;
            if (!TextUtils.isEmpty(mSubHead.getText().toString())) {
                text = mSubHead.getText().toString() + ", " + AccountUtils.getCompanyName(getActivity());
            } else {
                text = AccountUtils.getCompanyName(getActivity());
            }
            mSubHead.setText(text);
        }
        if (!TextUtils.isEmpty(mSubHead.getText().toString())) {
            mSubHead.setVisibility(View.VISIBLE);
        }
    }
}
