package de.bembelnaut.courses.dagger2forandroid.ui.main.posts;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.bembelnaut.courses.dagger2forandroid.SessionManager;
import de.bembelnaut.courses.dagger2forandroid.models.Post;
import de.bembelnaut.courses.dagger2forandroid.network.main.MainApi;
import de.bembelnaut.courses.dagger2forandroid.ui.main.Resource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PostsViewModel extends ViewModel {
    private static final String TAG = "PostsViewModel";

    private MainApi mainApi;
    private SessionManager sessionManager;
    private MediatorLiveData<Resource<List<Post>>> posts;

    @Inject
    public PostsViewModel(SessionManager sessionManager, MainApi mainApi) {
        this.mainApi = mainApi;
        this.sessionManager = sessionManager;

        Log.d(TAG, "PostsViewModel: created");
    }

    public LiveData<Resource<List<Post>>> observePosts() {
        if (posts == null) {
            posts = new MediatorLiveData<>();
            posts.setValue(Resource.loading((List<Post>) null));

            final LiveData<Resource<List<Post>>> source = LiveDataReactiveStreams.fromPublisher(
                    mainApi.getPostsFromUser(sessionManager.getAuthUser().getValue().data.getId())

                            .onErrorReturn(throwable -> {
                                Log.d(TAG, "apply: error...", throwable);
                                Post post = new Post();
                                post.setId(-1);
                                ArrayList<Post> result = new ArrayList<>();
                                result.add(post);
                                return result;
                            })

                            .map((Function<List<Post>, Resource<List<Post>>>) posts -> {
                                if (posts.size() > 0) {
                                    if (posts.get(0).getId() == -1) {
                                        return Resource.error("Something went wrong...", null);
                                    }
                                }

                                return Resource.success(posts);
                            })

                            .subscribeOn(Schedulers.io())
            );

            posts.addSource(source, listResource -> {
                posts.setValue(listResource);
                posts.removeSource(source);
            });
        }

        return posts;
    }
}
