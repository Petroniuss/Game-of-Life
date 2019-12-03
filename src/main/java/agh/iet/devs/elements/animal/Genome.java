package agh.iet.devs.elements.animal;

import java.util.Arrays;
import java.util.Random;

//Todo
public class Genome {
    private static final int genomeSize = 32;

    private final int[] genes = new int[genomeSize];

    public Genome() {
        var random = new Random();

        for (int i = 0; i < 8; i++)
            this.genes[i] = i;

        for(int i = 8; i < genomeSize; i++)
            this.genes[i] = random.nextInt(8);

        Arrays.sort(this.genes);
    }

    public Genome(Genome mom, Genome dad) {
        int[] groups = new int[genomeSize];

//        for int i =
    }


    private int geneAt(int i) {
        return this.genes[i];
    }

}
