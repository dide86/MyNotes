package ru.geekbrains.mynotes.domain;

public interface Callback<T> {
    void onSuccess(T value);
    void onError(Throwable error);
}
