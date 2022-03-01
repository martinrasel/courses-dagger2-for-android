package de.bembelnaut.courses.dagger2forandroid.di.auth;

import dagger.Module;
import dagger.Provides;
import de.bembelnaut.courses.dagger2forandroid.network.auth.AuthApi;
import retrofit2.Retrofit;

@Module
public class AuthModule {

    @AuthScope
    @Provides
    static AuthApi provideAuthApi(Retrofit retrofit) {
        return retrofit.create(AuthApi.class);
    }

}
