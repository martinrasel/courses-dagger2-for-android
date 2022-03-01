package de.bembelnaut.courses.dagger2forandroid.di.main;

import dagger.Module;
import dagger.Provides;
import de.bembelnaut.courses.dagger2forandroid.network.main.MainApi;
import de.bembelnaut.courses.dagger2forandroid.ui.main.posts.PostsRecyclerAdapter;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @MainScope
    @Provides
    static MainApi provideAuthApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }

    @MainScope
    @Provides
    static PostsRecyclerAdapter providePostsRecyclerAdapter() {
        PostsRecyclerAdapter recyclerAdapter = new PostsRecyclerAdapter();
        return recyclerAdapter;
    }
}
