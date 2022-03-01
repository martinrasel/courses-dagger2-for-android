package de.bembelnaut.courses.dagger2forandroid.di.main;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import de.bembelnaut.courses.dagger2forandroid.di.ViewModelKey;
import de.bembelnaut.courses.dagger2forandroid.ui.main.posts.PostsViewModel;
import de.bembelnaut.courses.dagger2forandroid.ui.main.profile.ProfileViewModel;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfileViewModel(ProfileViewModel profileViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel.class)
    public abstract ViewModel bindPostsViewModel(PostsViewModel postsViewModel);
}
