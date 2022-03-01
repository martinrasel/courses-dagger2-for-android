package de.bembelnaut.courses.dagger2forandroid.ui.auth;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import de.bembelnaut.courses.dagger2forandroid.R;
import de.bembelnaut.courses.dagger2forandroid.ui.main.MainActivity;
import de.bembelnaut.courses.dagger2forandroid.viewmodels.ViewModelProviderFactory;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AuthActivity";

    private AuthViewModel authViewModel;

    private EditText userId;

    private ProgressBar progressBar;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        userId = findViewById(R.id.user_id_input);
        progressBar = findViewById(R.id.progress_bar);
        findViewById(R.id.login_button).setOnClickListener(this);

        authViewModel = ViewModelProviders.of(this, providerFactory)
                .get(AuthViewModel.class);

        setLogo();
        subscribeObservers();
    }

    private void subscribeObservers() {
        authViewModel.observeAuthState().observe(this, userAuthResource -> {
            if (userAuthResource != null) {
               switch (userAuthResource.status) {
                   case LOADING:
                       showProgressBar(true);
                       break;
                   case AUTHENTICATED:
                       showProgressBar(false);
                       Log.d(TAG, "onChanged: LOGIN SUCCESS: " + userAuthResource.data.getEmail());
                       onLoginSuccess();
                       break;
                   case ERROR:
                       showProgressBar(false);
                       Toast.makeText(this, userAuthResource.message
                               + "\nDid you enter a number between 1 and 10?", Toast.LENGTH_SHORT).show();
                       break;
                   case NOT_AUTHENTICATED:
                       showProgressBar(false);
                       break;
               }
            }
        });
    }

    private void showProgressBar(boolean isVisible) {
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void setLogo() {
        requestManager.load(logo).into((ImageView) findViewById(R.id.login_logo));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                attemptLogin();
                break;
            
        }
    }

    private void attemptLogin() {
        if(TextUtils.isEmpty(userId.getText().toString())) {
            return;
        }

        authViewModel.authenticateWithId(Integer.parseInt(userId.getText().toString()));
    }

    private void onLoginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}