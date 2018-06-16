package com.systers.conference.ui.online.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.systers.conference.R;
import com.systers.conference.ui.online.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Login screen.
 */
public class LoginActivity extends AppCompatActivity implements LogInMvpView {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;

    private LoginPresenter logInPresenter;
    private Intent intent;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        logInPresenter = new LoginPresenter();
        logInPresenter.attachView(this);
    }

    @OnClick(R.id.btn_log_in)
    void logIn() {
        //TODO: Add more validation
        String userName = etUsername.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            etUsername.setError(getString(R.string.enter_username));
            return;
        }

        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.enter_password));
            return;
        }

        logInPresenter.performLogIn(userName, password);
        showProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        logInPresenter.detachView();
    }

    @Override
    public void logInSuccessful() {
        hideProgressDialog();
        Toast.makeText(this, R.string.login_successful, Toast.LENGTH_SHORT).show();
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void logInFailed(@Nullable String errorMessage) {
        hideProgressDialog();
        if (TextUtils.isEmpty(errorMessage)) {
            errorMessage = getString(R.string.login_unsuccessful);
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
        progressDialog.setMessage(getString(R.string.logging_in));
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}

