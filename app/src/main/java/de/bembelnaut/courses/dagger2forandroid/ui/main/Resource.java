package de.bembelnaut.courses.dagger2forandroid.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Resource<T> {

    @NonNull
    public final Resource.ResourceStatus status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;

    public Resource(@NonNull Resource.ResourceStatus status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(ResourceStatus.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(@NonNull String msg, @Nullable T data) {
        return new Resource<>(Resource.ResourceStatus.ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Resource.ResourceStatus.LOADING, data, null);
    }

    public enum ResourceStatus {SUCCESS, ERROR, LOADING}

}
