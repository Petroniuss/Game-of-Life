package agh.iet.devs.data;

public class Tuple<E> {

    public final E first;
    public final E second;

    private Tuple(E first, E second) {
        this.first = first;
        this.second = second;
    }

    public static <E> Tuple<E> of(E first, E second) {
        return new Tuple<>(first, second);
    }

}
