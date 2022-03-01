package de.bembelnaut.courses.dagger2forandroid.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import de.bembelnaut.courses.dagger2forandroid.SessionManager;
import de.bembelnaut.courses.dagger2forandroid.models.User;
import de.bembelnaut.courses.dagger2forandroid.network.auth.AuthApi;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {
    private static final String TAG = "AuthViewModel";

    private final AuthApi authApi;

    private SessionManager sessionManager;

    @Inject
    public AuthViewModel(AuthApi authApi, SessionManager sessionManager) {
        this.authApi = authApi;
        this.sessionManager = sessionManager;
        Log.d(TAG, "AuthViewModel: created!");
    }

    public void authenticateWithId(int userId) {
        Log.d(TAG, "authenticateWithId: attempting to log in");

        sessionManager.authenticateWithId(queryUserId(userId));
    }

    private LiveData<AuthResource<User>> queryUserId(int userId) {
        return LiveDataReactiveStreams
                .fromPublisher(authApi.getUser(userId)

                    // called if an error happens
                    .onErrorReturn(throwable -> {
                        User errorUser = new User();
                        errorUser.setId(-1);
                        return errorUser;
                    })

                    // default mapping function, map User to an AuthResource<User>
                    .map((Function<User, AuthResource<User>>) user -> {
                        if (user.getId() == -1) {
                            return AuthResource.error("Could not authenticate", (User) null);
                        }
                        return AuthResource.authenticated(user);
                    })

                    .subscribeOn(Schedulers.io()));
    }

    public LiveData<AuthResource<User>> observeAuthState() {
        return sessionManager.getAuthUser();
    }
}
