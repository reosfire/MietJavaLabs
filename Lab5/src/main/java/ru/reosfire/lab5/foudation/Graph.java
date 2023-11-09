package ru.reosfire.lab5.foudation;

import java.awt.*;
import java.util.Arrays;
import java.util.OptionalDouble;

public class Graph {
    private final double[] xs;
    private final double[] ys;
    private final Color color;
    private final String label;

    public Graph(double[] xs, double[] ys, Color color, String label) {
        if (xs.length != ys.length) throw new IllegalArgumentException("xs size does not match ys size");

        this.xs = xs;
        this.ys = ys;
        this.color = color;
        this.label = label;
    }

    public Point get(int i) {
        return new Point(xs[i], ys[i]);
    }

    public Color getColor() {
        return color;
    }

    public String getLabel() {
        return label;
    }

    public int size() {
        return xs.length;
    }

    public double maxY() {
        OptionalDouble max = Arrays.stream(ys).max();
        if (!max.isPresent()) throw new RuntimeException("empty ys array?");
        return max.getAsDouble();
    }
    public double maxX() {
        OptionalDouble max = Arrays.stream(xs).max();
        if (!max.isPresent()) throw new RuntimeException("empty xs array?");
        return max.getAsDouble();
    }
    public double minY() {
        OptionalDouble min = Arrays.stream(ys).min();
        if (!min.isPresent()) throw new RuntimeException("empty ys array?");
        return min.getAsDouble();
    }
    public double minX() {
        OptionalDouble min = Arrays.stream(xs).min();
        if (!min.isPresent()) throw new RuntimeException("empty xs array?");
        return min.getAsDouble();
    }
}
