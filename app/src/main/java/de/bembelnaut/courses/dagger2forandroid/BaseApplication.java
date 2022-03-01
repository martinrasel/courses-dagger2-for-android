package de.bembelnaut.courses.dagger2forandroid;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import de.bembelnaut.courses.dagger2forandroid.di.DaggerAppComponent;

public class BaseApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
