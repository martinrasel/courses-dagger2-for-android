package de.bembelnaut.courses.dagger2forandroid.di.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import de.bembelnaut.courses.dagger2forandroid.ui.main.posts.PostsFragment;
import de.bembelnaut.courses.dagger2forandroid.ui.main.profile.ProfileFragment;

@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract PostsFragment contributePostsFragment();
}
