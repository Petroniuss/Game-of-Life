package agh.iet.devs.config;

import agh.iet.devs.data.Rect;
import agh.iet.devs.data.Vector;

public class Params {
    public final int width;
    public final int height;
    public final int startEnergy;
    public final int moveEnergy;
    public final int plantEnergy;
    public final double jungleRatio;
    public final int animalsAtStart;
    public final boolean parallel;

    public Params(int width, int height, int startEnergy, int moveEnergy, int plantEnergy, double jungleRatio, int animalsAtStart, boolean parallel) {
        this.width = width;
        this.height = height;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.jungleRatio = jungleRatio;
        this.animalsAtStart = animalsAtStart;
        this.parallel = parallel;
    }

    public Rect outerBounds() {
        final Vector lowerLeft = Vector.create(0, 0);
        final Vector upperRight = Vector.create(width - 1, height - 1);

        return new Rect(lowerLeft, upperRight);
    }

    public Rect jungleBounds() {
        final var jungleWidth = (int) (width * jungleRatio);
        final var jungleHeight = (int) (height * jungleRatio);

        final var startX = (width - jungleWidth) / 2;
        final var endX = startX + jungleWidth;
        final var startY = (height - jungleHeight) / 2;
        final var endY = startY + jungleHeight;

        return new Rect(Vector.create(startX, startY), Vector.create(endX, endY));
    }

    @Override
    public String toString() {
        return "Params{" +
                "width=" + width +
                ", height=" + height +
                ", startEnergy=" + startEnergy +
                ", moveEnergy=" + moveEnergy +
                ", plantEnergy=" + plantEnergy +
                ", jungleRatio=" + jungleRatio +
                ", animalsAtStart=" + animalsAtStart +
                '}';
    }

}
