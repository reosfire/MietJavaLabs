package ru.reosfire.lab5.frames;

import ru.reosfire.lab5.components.Plotter;
import ru.reosfire.lab5.foudation.Graph;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;

public class MainFrame extends JFrame {
    private final static int HEIGHT = 1080;
    private final static int WIDTH = 1920;

    private final Plotter graphComponent = new Plotter();

    public MainFrame() throws HeadlessException {
        setSize(WIDTH, HEIGHT);
        add(graphComponent);
        graphComponent.setContent(Collections.singletonList(generateExampleGraph()));
    }

    private Graph generateExampleGraph() {
        double[] xs = new double[1000];
        double[] ys = new double[1000];

        for (int i = 0; i < xs.length; i++) {
            xs[i] = i / 100.0 - 5;
            ys[i] = xs[i] * xs[i];
        }
        return new Graph(xs, ys, Color.BLUE, "x^2");
    }
}
