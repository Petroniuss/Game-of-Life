package agh.iet.devs.config;

public class Params {
    public final int width;
    public final int height;
    public final int startEnergy;
    public final int moveEnergy;
    public final int plantEnergy;
    public final double jungleRatio;
    public final int animalsAtStart;

    public Params(int width, int height, int startEnergy, int moveEnergy, int plantEnergy, double jungleRatio, int animalsAtStart) {
        this.width = width - 1;
        this.height = height - 1;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.jungleRatio = jungleRatio;
        this.animalsAtStart = animalsAtStart;
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
