package ru.reosfire.lab5.foudation;

public class Measurement {
    public final double xShift;
    public final double yShift;
    public final double xFactor;
    public final double yFactor;

    public final double maxX;
    public final double maxY;
    public final double minX;
    public final double minY;

    public Measurement(double xShift, double yShift, double xFactor, double yFactor, double maxX, double maxY, double minX, double minY) {
        this.xShift = xShift;
        this.yShift = yShift;
        this.xFactor = xFactor;
        this.yFactor = yFactor;
        this.maxX = maxX;
        this.maxY = maxY;
        this.minX = minX;
        this.minY = minY;
    }

    public int xToPx(double x) {
         return (int)Math.round(x * xFactor + xShift);
    }
    public int yToPx(double y) {
        return (int)Math.round(y * yFactor + yShift);
    }

    public double getWidth() {
        return maxX - minX;
    }
    public double getHeight() {
        return maxY - minY;
    }

    public double[] getHorizontalGrid() {
        double step = getGridStep(getWidth());
        return getGrid(step);
    }
    public double[] getVerticalGrid() {
        double step = getGridStep(getHeight());
        return getGrid(step);
    }

    private double[] getGrid(double step) {
        double[] result = new double[78];

        int k = 0;

        double curr = 0;
        for (int i = 0; i < 39; i++, k++) {
            curr += step;
            result[k] = curr;
        }

        curr = 0;
        for (int i = 0; i < 39; i++, k++) {
            curr -= step;
            result[k] = curr;
        }

        return result;
    }

    private double getGridStep(double fraction) {
        return Math.pow(10, Math.round(Math.log10(fraction)) - 1);
    }
}
