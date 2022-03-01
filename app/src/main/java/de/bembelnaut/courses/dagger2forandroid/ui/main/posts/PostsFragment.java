package de.bembelnaut.courses.dagger2forandroid.ui.main.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import de.bembelnaut.courses.dagger2forandroid.R;
import de.bembelnaut.courses.dagger2forandroid.util.VerticalSpacingItemDecoration;
import de.bembelnaut.courses.dagger2forandroid.viewmodels.ViewModelProviderFactory;

public class PostsFragment extends DaggerFragment {
    private static final String TAG = "PostsFragment";

    @Inject
    ViewModelProviderFactory providerFactory;

    PostsViewModel postsViewModel;

    RecyclerView recyclerView;

    @Inject
    PostsRecyclerAdapter recyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);
        postsViewModel = ViewModelProviders.of(this, providerFactory).get(PostsViewModel.class);

        initRecyclerView();
        subscribeObservers();
    }

    private void initRecyclerView() {
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VerticalSpacingItemDecoration decorator = new VerticalSpacingItemDecoration(15);
        recyclerView.addItemDecoration(decorator);
    }

    private void subscribeObservers() {
        postsViewModel.observePosts().removeObservers(getViewLifecycleOwner());
        postsViewModel.observePosts().observe(getViewLifecycleOwner(), listResource -> {
            if (listResource != null) {
                switch (listResource.status) {
                    case LOADING:
                        Log.d(TAG, "subscribeObservers: LOADING...");
                        break;
                    case ERROR:
                        Log.e(TAG, "subscribeObservers: Error" + listResource.message );
                        break;
                    case SUCCESS:
                        recyclerAdapter.setPosts(listResource.data);
                        break;
                }
            }
        });
    }
}
