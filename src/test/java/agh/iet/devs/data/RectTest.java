package agh.iet.devs.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectTest {

    @Test
    void testIterator_firstAndLast() {
        final var rect = new Rect(Vector.create(0, 0), Vector.create(1, 1));
        final var iterator = rect.iterator();
        final var first = iterator.next();

        var last = first;
        while (iterator.hasNext()) {
            last = iterator.next();
        }

        assertEquals(Vector.create(0, 0), first);
        assertEquals(Vector.create(1, 1), last);
    }
}