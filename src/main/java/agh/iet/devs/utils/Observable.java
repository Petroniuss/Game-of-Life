package agh.iet.devs.utils;

public interface Observable<T> {

    void attachListener(T listener);

    void detachListener(T listener);

}
