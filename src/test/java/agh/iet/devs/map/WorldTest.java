package agh.iet.devs.map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {

    @Test
    void testModifyingWhileIteratingOverShallowCopyOfMap() {
        var map = new HashMap<Integer, String>();

        map.put(1, "a");
        map.put(2, "b");

        Executable executable = () -> {
            new HashSet<>(map.entrySet()).stream()
                    .map(Map.Entry::getKey)
                    .forEach(map::remove);
        };

        assertDoesNotThrow(executable);
        assertTrue(map.isEmpty());
    }

    @Test
    void testModifyingWhileIteratingOverMap_thenFail() {
        var map = new HashMap<Integer, String>();

        map.put(1, "a");
        map.put(2, "b");

        assertThrows(ConcurrentModificationException.class, () -> map.keySet().forEach(map::remove));
    }

}