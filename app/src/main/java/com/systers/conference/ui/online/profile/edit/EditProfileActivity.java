package com.systers.conference.ui.online.profile.edit;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.mvc.imagepicker.ImagePicker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.systers.conference.R;
import com.systers.conference.ui.online.MainActivity;
import com.systers.conference.utils.AccountUtils;
import com.systers.conference.utils.LogUtils;
import com.systers.conference.utils.PermissionsUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity implements EditProfileMvpView{

    private static final String LOG_TAG = LogUtils.makeLogTag(EditProfileActivity.class);
    private static final String[] RUN_TIME_PERMISSIONS = Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 ? new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE} : new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int PERMISSION_CALLBACK = 100;
    @BindView(R.id.profile_coordinator_layout)
    CoordinatorLayout mLayout;
    @BindView(R.id.avatar)
    CircleImageView mAvatar;
    @BindView(R.id.edit_icon)
    FloatingActionButton mEditIcon;
    @BindView(R.id.edit_first_name)
    EditText mFirstName;
    @BindView(R.id.edit_last_name)
    EditText mLastName;
    @BindView(R.id.edit_email)
    EditText mEmail;
    @BindView(R.id.edit_company_name)
    EditText mCompanyName;
    @BindView(R.id.edit_role)
    EditText mRole;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.text_input_firstname)
    TextInputLayout mTextFirstName;
    @BindView(R.id.text_input_last_name)
    TextInputLayout mTextLastName;
    private boolean mIsAvatarPresent;

    private EditProfilePresenter editProfilePresenter;
    private ProgressDialog progressDialog;

    @OnClick(R.id.edit_icon)
    public void editAvatar() {
        if (PermissionsUtil.areAllRunTimePermissionsGranted(RUN_TIME_PERMISSIONS, this)) {
            final CharSequence[] items = mIsAvatarPresent ? new CharSequence[]{getString(R.string.edit_avatar), getString(R.string.delete_avatar)}
                    : new CharSequence[]{getString(R.string.edit_avatar)};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (items[which].equals(getString(R.string.edit_avatar))) {
                        dialog.dismiss();
                        ImagePicker.pickImage(EditProfileActivity.this, getString(R.string.select_avatar));
                    } else if (items[which].equals(getString(R.string.delete_avatar))) {
                        dialog.dismiss();
                        deleteOldAvatar();
                        AccountUtils.setProfilePictureUrl(EditProfileActivity.this, null);
                        updateAvatar();
                    }
                }
            });
            builder.show();
        } else {
            LogUtils.LOGE(LOG_TAG, "Permission not granted");

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        editProfilePresenter = new EditProfilePresenter();
        editProfilePresenter.attachView(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImagePicker.setMinQuality(getResources().getInteger(R.integer.avatar_dimen), getResources().getInteger(R.integer.avatar_dimen));
    }

    private void deleteOldAvatar() {
        if (AccountUtils.getProfilePictureUrl(this) != null) {
            if (!Patterns.WEB_URL.matcher(AccountUtils.getProfilePictureUrl(this)).matches()) {
                Uri uri = Uri.parse(AccountUtils.getProfilePictureUrl(this));
                getContentResolver().delete(uri, null, null);
            }
        }
    }

    private void updateAvatar() {
        Drawable icon = AppCompatResources.getDrawable(this, R.drawable.ic_photo_camera_black_24dp);
        mEditIcon.setIconDrawable(icon);
        if (AccountUtils.getProfilePictureUrl(this) != null) {
            Picasso.with(this)
                    .load(Uri.parse(AccountUtils.getProfilePictureUrl(this)))
                    .resize(getResources().getInteger(R.integer.avatar_dimen), getResources().getInteger(R.integer.avatar_dimen))
                    .centerCrop()
                    .placeholder(R.drawable.male_icon_9_glasses)
                    .error(R.drawable.male_icon_9_glasses)
                    .into(mAvatar, new Callback() {
                        @Override
                        public void onSuccess() {
                            mIsAvatarPresent = true;
                        }

                        @Override
                        public void onError() {
                            mIsAvatarPresent = false;
                        }
                    });
        } else {
            mAvatar.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.male_icon_9_glasses));
            mIsAvatarPresent = false;
        }
    }

    @Override
    public void onBackPressed() {
        attemptToSave(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        if (id == R.id.action_check) {
            attemptToSave(false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void attemptToSave(boolean isBackPressed) {
        mTextFirstName.setError(null);
        mTextLastName.setError(null);
        String firstName = mFirstName.getText().toString();
        String lastName = mLastName.getText().toString();
        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(firstName)) {
            mTextFirstName.setError(getString(R.string.error_field_required));
            focusView = mFirstName;
            cancel = true;
        } else if (TextUtils.isEmpty(lastName)) {
            mTextLastName.setError(getString(R.string.error_field_required));
            focusView = mLastName;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            saveChanges(isBackPressed);
        }
    }


    private void saveChanges(boolean isBackPressed) {
        LogUtils.LOGE(LOG_TAG, mFirstName.getText().toString() + ' ' + AccountUtils.getFirstName(this));
        final Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(getString(R.string.edit_profile), true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        boolean dataChanged = !((mFirstName.getText().toString().equals(AccountUtils.getFirstName(this))) &&
                (mLastName.getText().toString().equals(AccountUtils.getLastName(this))) &&
                (mCompanyName.getText().toString().equals(AccountUtils.getCompanyName(this))) &&
                (mRole.getText().toString().equals(AccountUtils.getCompanyRole(this))));
        if (isBackPressed) {
            if (dataChanged) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(false);
                builder.setMessage("Are you sure you want to discard your changes?");
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        editProfilePresenter.updateProfile();
                        saveChanges(false);
                    }
                });
                builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(intent);
                        finish();
                    }
                });
                builder.show();
            } else {
                startActivity(intent);
                finish();
            }
        } else {
            if (dataChanged) {
                AccountUtils.setFirstName(this, mFirstName.getText().toString());
                AccountUtils.setLastName(this, mLastName.getText().toString());
                AccountUtils.setCompanyName(this, mCompanyName.getText().toString());
                AccountUtils.setCompanyRole(this, mRole.getText().toString());
                Toast.makeText(this, getString(R.string.save_toast), Toast.LENGTH_LONG).show();
                editProfilePresenter.updateProfile();
            }
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void loadProfile() {
        mFirstName.setText(AccountUtils.getFirstName(this));
        mLastName.setText(AccountUtils.getLastName(this));
        mEmail.setText(AccountUtils.getEmail(this));
        mCompanyName.setText(AccountUtils.getCompanyName(this));
        mRole.setText(AccountUtils.getCompanyRole(this));
        updateAvatar();
    }

    @Override
    public void profileUpdateSuccessful() {
        loadProfile();
        hideProgressDialog();
        Toast.makeText(this, R.string.profile_updated, Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    public void profileUpdateFailed(@Nullable String errorMessage) {
        hideProgressDialog();
        if (TextUtils.isEmpty(errorMessage)) {
            errorMessage = getString(R.string.profile_update_failed);
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
            //TODO: Set this to false
            progressDialog.setCancelable(true);
        }
        progressDialog.setMessage(getString(R.string.updating_profile) );
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
