package de.bembelnaut.courses.dagger2forandroid.di.auth;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import de.bembelnaut.courses.dagger2forandroid.di.ViewModelKey;
import de.bembelnaut.courses.dagger2forandroid.ui.auth.AuthViewModel;

@Module
public abstract class AuthViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel viewModel);
}
