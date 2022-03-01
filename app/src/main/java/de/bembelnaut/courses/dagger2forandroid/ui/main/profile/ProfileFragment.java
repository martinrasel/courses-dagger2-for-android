package de.bembelnaut.courses.dagger2forandroid.ui.main.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import de.bembelnaut.courses.dagger2forandroid.R;
import de.bembelnaut.courses.dagger2forandroid.models.User;
import de.bembelnaut.courses.dagger2forandroid.viewmodels.ViewModelProviderFactory;

public class ProfileFragment extends DaggerFragment {
    private static final String TAG = "ProfileFragment";

    @Inject
    ViewModelProviderFactory providerFactory;

    private ProfileViewModel profileViewModel;

    private TextView email, username, website;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: created....");
        profileViewModel = ViewModelProviders.of(this, providerFactory).get(ProfileViewModel.class);

        email = view.findViewById(R.id.email);
        username = view.findViewById(R.id.username);
        website = view.findViewById(R.id.website);

        subscribeObservers();
    }

    private void subscribeObservers() {
        profileViewModel.getAuthenticateUser().removeObservers(getViewLifecycleOwner());

        profileViewModel.getAuthenticateUser().observe(getViewLifecycleOwner(), userAuthResource -> {
            if (userAuthResource != null) {
                switch (userAuthResource.status) {
                    case AUTHENTICATED:
                        setUserDetails(userAuthResource.data);
                        break;
                    case ERROR:
                        setErrorDetails(userAuthResource.message);
                        break;
                }
            }
        });
    }

    private void setErrorDetails(String message) {
        email.setText(message);
        website.setText("error");
        username.setText("error");
    }

    private void setUserDetails(User data) {
        email.setText(data.getEmail());
        website.setText(data.getWebsite());
        username.setText(data.getUsername());
    }
}
