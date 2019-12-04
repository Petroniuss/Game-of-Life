package agh.iet.devs.elements.animal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.imageio.plugins.tiff.GeoTIFFTagSet;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class GenomeTest {

    private final Collection<Integer> possibleGenes = List.of(0, 1, 2, 3, 4, 5, 6, 7);

    @Test
    void testIfGenomeContainsAllPossibleGenes() {
        final var genome = new Genome();

        assertTrue(IntStream.range(0, 10000)
                .map(i -> genome.predict())
                .boxed()
                .collect(Collectors.toSet())
                .containsAll(possibleGenes));
    }

    @Test
    void testIfCrossedGenomeContainsAllPossibleGenes() {
        final var g1 = new Genome();
        final var g2 = new Genome();

        final var tested = new Genome(g1, g2);

        assertTrue(IntStream.range(0, 10000)
                .map(i -> tested.predict())
                .boxed()
                .collect(Collectors.toSet())
                .containsAll(possibleGenes));
    }

}





