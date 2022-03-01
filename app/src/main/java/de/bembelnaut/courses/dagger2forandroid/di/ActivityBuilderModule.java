package de.bembelnaut.courses.dagger2forandroid.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import de.bembelnaut.courses.dagger2forandroid.di.auth.AuthModule;
import de.bembelnaut.courses.dagger2forandroid.di.auth.AuthScope;
import de.bembelnaut.courses.dagger2forandroid.di.auth.AuthViewModelsModule;
import de.bembelnaut.courses.dagger2forandroid.di.main.MainFragmentBuildersModule;
import de.bembelnaut.courses.dagger2forandroid.di.main.MainModule;
import de.bembelnaut.courses.dagger2forandroid.di.main.MainScope;
import de.bembelnaut.courses.dagger2forandroid.di.main.MainViewModelsModule;
import de.bembelnaut.courses.dagger2forandroid.ui.auth.AuthActivity;
import de.bembelnaut.courses.dagger2forandroid.ui.main.MainActivity;

@Module
public abstract class ActivityBuilderModule {

    @AuthScope
    @ContributesAndroidInjector(
            modules = {
                    AuthViewModelsModule.class,
                    AuthModule.class})
    abstract AuthActivity contributeAuthActivity();

    @MainScope
    @ContributesAndroidInjector(
            modules = {
                    MainFragmentBuildersModule.class,
                    MainViewModelsModule.class,
                    MainModule.class})
    abstract MainActivity contributeMainActivity();
}
