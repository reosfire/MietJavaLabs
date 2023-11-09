package ru.reosfire.lab5.foudation;

public class MeasurementResult {
    public final double xShift;
    public final double yShift;
    public final double xFactor;
    public final double yFactor;

    public MeasurementResult(double xShift, double yShift, double xFactor, double yFactor) {
        this.xShift = xShift;
        this.yShift = yShift;
        this.xFactor = xFactor;
        this.yFactor = yFactor;
    }
}
