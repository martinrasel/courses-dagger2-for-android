package de.bembelnaut.courses.dagger2forandroid.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import de.bembelnaut.courses.dagger2forandroid.SessionManager;
import de.bembelnaut.courses.dagger2forandroid.models.User;
import de.bembelnaut.courses.dagger2forandroid.ui.auth.AuthResource;

public class ProfileViewModel extends ViewModel {
    private static final String TAG = "ProfileViewModel";

    private SessionManager sessionManager;

    @Inject
    public ProfileViewModel(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        Log.d(TAG, "ProfileViewModel: viewmodel is ready....");
    }

    public LiveData<AuthResource<User>> getAuthenticateUser() {
        return sessionManager.getAuthUser();
    }
}
