package de.bembelnaut.courses.dagger2forandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import de.bembelnaut.courses.dagger2forandroid.ui.auth.AuthActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Inject
    public SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        subscribeObserver();
    }

    private void subscribeObserver() {
        sessionManager.getAuthUser().observe(this, userAuthResource -> {
            if (userAuthResource != null) {
                switch (userAuthResource.status) {
                    case LOADING:

                        break;
                    case AUTHENTICATED:

                        Log.d(TAG, "onChanged: LOGIN SUCCESS: " + userAuthResource.data.getEmail());
                        break;
                    case ERROR:

                        Toast.makeText(this, userAuthResource.message
                                + "\nDid you enter a number between 1 and 10?", Toast.LENGTH_SHORT).show();
                        break;
                    case NOT_AUTHENTICATED:
                        navLoginScreen();
                        break;
                }
            }
        });
    }

    private void navLoginScreen() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }
}
