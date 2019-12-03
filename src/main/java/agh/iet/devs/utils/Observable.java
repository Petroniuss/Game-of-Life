package agh.iet.devs.utils;

/**
 * @param <T> listener which should provide some callback
 * Note that this interface does not contain notify method and the reason for that is
 * One class can implement this interface multiple times and so we would have a collision.
 * Therefore it's best to create another interface providing notifySomeListeners method.
 */
public interface Observable<T> {

    void attachListener(T listener);

    void detachListener(T listener);

}
