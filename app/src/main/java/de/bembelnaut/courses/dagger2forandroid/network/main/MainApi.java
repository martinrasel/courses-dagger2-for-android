package de.bembelnaut.courses.dagger2forandroid.network.main;

import java.util.List;

import de.bembelnaut.courses.dagger2forandroid.models.Post;
import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainApi {

    @GET("/posts")
    Flowable<List<Post>> getPostsFromUser(@Query("userId") int userId);
}
