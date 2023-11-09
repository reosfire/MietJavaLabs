package ru.reosfire.lab5.frames;

import ru.reosfire.lab5.components.Plotter;
import ru.reosfire.lab5.foudation.Graph;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class MainFrame extends JFrame {
    private final static int HEIGHT = 1080;
    private final static int WIDTH = 1920;

    private final Plotter graphComponent = new Plotter();

    public MainFrame() throws HeadlessException {
        setSize(WIDTH, HEIGHT);
        add(graphComponent);
        graphComponent.setContent(generateExampleGraphs());
    }

    private List<Graph> generateExampleGraphs() {
        double[] xs = new double[1000];
        double[] ys = new double[1000];

        for (int i = 0; i < xs.length; i++) {
            xs[i] = i / 100.0 - 5;
            ys[i] = xs[i] * xs[i];
        }

        double[] xs1 = new double[1000];
        double[] ys1 = new double[1000];
        for (int i = 0; i < xs.length; i++) {
            xs1[i] = i / 200.0 - 2.5;
            ys1[i] = xs1[i] * xs1[i] * xs1[i];
        }

        return Arrays.asList(
                new Graph(xs, ys, Color.BLUE, "x^2"),
                new Graph(xs1, ys1, Color.RED, "x^3")
        );
    }
}
