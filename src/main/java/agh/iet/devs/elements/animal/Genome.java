package agh.iet.devs.elements.animal;

import java.util.*;
import java.util.stream.Collectors;

public class Genome {
    private static final Collection<Integer> possibleGenes = List.of(0, 1, 2, 3, 4, 5, 6, 7);
    private static final Random random = new Random();
    private static final int genomeSize = 32;

    private final int[] genes = new int[genomeSize];

    public Genome() {
        for (int i = 0; i < 8; i++)
            this.genes[i] = i;
        for (int i = 8; i < genomeSize; i++)
            this.genes[i] = random.nextInt(8);

        Arrays.sort(this.genes);
    }

    /**
     * FIXME - this might be causing bugs!
     */
    public Genome(Genome g1, Genome g2) {
        final var partition1 = random.nextInt(genomeSize - 3) + 1; // max genomeSize - 3, min 1
        final var partition2 = random.nextInt(genomeSize - partition1 - 1) + partition1 + 1; // max genomeSize - 1, min 2

        if (random.nextBoolean()) {
            final var holder = g1;
            g1 = g2;
            g2 = holder;
        }

        for (int i = 0; i < partition1; i++)
            this.genes[i] = g1.geneAt(i);

        for (int i = partition1; i < partition2; i++)
            this.genes[i] = g2.geneAt(i);

        for (int i = partition2; i < genomeSize; i++)
            this.genes[i] = g1.geneAt(i);

        // Fixme - infinite loop..
        while (!verify()) {
            final var set = genesToSet();
            possibleGenes.stream()
                    .filter(set::contains)
                    .forEach(this::flipRandomGeneTo);
        }
    }

    /**
     * @return int denoting how many rotations animal should do.
     */
    public int predict() {
        final var i = random.nextInt(genomeSize);

        return genes[i];
    }

    private int geneAt(int i) {
        return this.genes[i];
    }

    private void flipRandomGeneTo(int gene) {
        final var i = random.nextInt(genomeSize);
        genes[i] = gene;
    }

    private boolean verify() {
        return genesToSet().containsAll(possibleGenes);
    }

    private Set<Integer> genesToSet() {
        return Arrays.stream(this.genes)
                .boxed()
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "Genes = " + Arrays.toString(genes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genome)) return false;

        Genome genome = (Genome) o;

        return Arrays.equals(genes, genome.genes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(genes);
    }
}
