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
        this.width = width;
        this.height = height;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Params)) return false;

        Params params = (Params) o;

        if (width != params.width) return false;
        if (height != params.height) return false;
        if (startEnergy != params.startEnergy) return false;
        if (moveEnergy != params.moveEnergy) return false;
        if (plantEnergy != params.plantEnergy) return false;
        if (Double.compare(params.jungleRatio, jungleRatio) != 0) return false;
        return animalsAtStart == params.animalsAtStart;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = width;
        result = 31 * result + height;
        result = 31 * result + startEnergy;
        result = 31 * result + moveEnergy;
        result = 31 * result + plantEnergy;
        temp = Double.doubleToLongBits(jungleRatio);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + animalsAtStart;
        return result;
    }
}
