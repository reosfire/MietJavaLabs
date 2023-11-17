package ru.reosfire.lab5.frames;

import ru.reosfire.lab5.coloring.ColorsRegistry;
import ru.reosfire.lab5.coloring.NamedColor;
import ru.reosfire.lab5.components.Plotter;
import ru.reosfire.lab5.data.FileGraphReader;
import ru.reosfire.lab5.foudation.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainFrame extends JFrame {
    private final static int HEIGHT = 1080;
    private final static int WIDTH = 1920;

    private final Plotter graphComponent = new Plotter();

    private final ColorsRegistry colorsRegistry = new ColorsRegistry();

    private final List<Graph> loadedGraphs = new ArrayList<>();

    private boolean appendModeEnabled = false;
    private boolean logModeEnabled = false;

    private String selectedColorName = null;

    public MainFrame() throws HeadlessException {
        setSize(WIDTH, HEIGHT);
        setLayout(new GridBagLayout());

        inflate();


        final double[] current = {1};
        final double[] delta = {0.002};

        new Thread(() -> {
            while (true) {
                current[0] += delta[0];
                if (current[0] > 2) delta[0] = -0.001;
                else if (current[0] < 0.002) delta[0] = 0.001;
                List<Graph> exampleGraphs = generateExampleGraphs(0, current[0]);
                List<Graph> result = new ArrayList<>();
                synchronized (loadedGraphs) {
                    if (logModeEnabled) {
                        for (Graph exampleGraph : exampleGraphs) {
                            result.add(exampleGraph.toLogScaled());
                        }
                        for (Graph loadedGraph : loadedGraphs) {
                            result.add(loadedGraph.toLogScaled());
                        }
                    } else {
                        result.addAll(exampleGraphs);
                        result.addAll(loadedGraphs);
                    }
                }
                graphComponent.setContent(result);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void inflate() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;

        gbc.weightx = 0.1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(getMenuComponent(), gbc);

        gbc.weightx = 0.9;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(graphComponent, gbc);
    }

    private Component getMenuComponent() {
        Panel panel = new Panel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(getPathConfirmButton(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(getAppendModeCheckBox(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(getLogScaleModeCheckBox(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(getColorChoice(), gbc);

        return panel;
    }

    private Button getPathConfirmButton() {
        Button button = new Button("Load graph from file");
        button.addActionListener(it -> {
            FileDialog fd = new FileDialog(this, "Choose a file", FileDialog.LOAD);
            fd.setDirectory("C:\\");
            fd.setFile("*.grf");
            fd.setVisible(true);

            try {
                String dir = fd.getDirectory();
                String file = fd.getFile();
                if (file != null && dir != null) {
                    synchronized (loadedGraphs) {
                        if (appendModeEnabled) {
                            Color color = colorsRegistry.getColorByName(selectedColorName).color;
                            Graph graph = FileGraphReader.readFrom(dir + file, color);
                            loadedGraphs.add(graph);

                            colorsRegistry.markAsUsed(color);
                        } else {
                            loadedGraphs.clear();
                            colorsRegistry.markAllUnused();

                            Color color = colorsRegistry.getColorByName(selectedColorName).color;
                            Graph graph = FileGraphReader.readFrom(dir + file, color);
                            loadedGraphs.add(graph);

                            colorsRegistry.markAsUsed(color);
                        }
                    }
                }
            } catch (Exception e) {
                makeDialog("Error", e.toString());
            }
        });
        return button;
    }

    private Checkbox getAppendModeCheckBox() {
        Checkbox checkbox = new Checkbox("Append graph to existed figure");
        checkbox.addItemListener(it -> {
            int currentState = it.getStateChange();
            if (currentState == ItemEvent.SELECTED) {
                appendModeEnabled = true;
            } else if (currentState == ItemEvent.DESELECTED) {
                appendModeEnabled = false;
            }
        });
        return checkbox;
    }

    private Checkbox getLogScaleModeCheckBox() {
        Checkbox checkbox = new Checkbox("Use log scale");
        checkbox.addItemListener(it -> {
            int currentState = it.getStateChange();
            if (currentState == ItemEvent.SELECTED) {
                logModeEnabled = true;
            } else if (currentState == ItemEvent.DESELECTED) {
                logModeEnabled = false;
            }
        });
        return checkbox;
    }

    private Panel getSpacer() {
        Panel panel = new Panel();
        panel.setMinimumSize(new Dimension(100, 500));
        panel.setSize(new Dimension(1000, 500));
        return panel;
    }

    private Choice getColorChoice() {
        Choice colorChoice = new Choice();
        for (NamedColor unusedColor : colorsRegistry.getAllUnusedColors()) {
            colorChoice.add(unusedColor.name);
        }
        selectedColorName = colorChoice.getSelectedItem();

        colorsRegistry.addOnChangeListener(() -> {
            colorChoice.removeAll();
            for (NamedColor unusedColor : colorsRegistry.getAllUnusedColors()) {
                colorChoice.add(unusedColor.name);
            }

            colorChoice.select(selectedColorName);
            selectedColorName = colorChoice.getSelectedItem();
        });

        colorChoice.addItemListener(it -> {
            selectedColorName = (String) it.getItem();
        });
        return colorChoice;
    }

    private void makeDialog(String title, String content) {
        Dialog dialog = new Dialog(this, title, true);
        dialog.setLayout(new FlowLayout());

        dialog.add(new Label(content));

        Button ok = new Button("OK");
        ok.addActionListener(it -> {
            dialog.setVisible(false);
        });
        dialog.add(ok);

        dialog.setSize(300, 200);
        dialog.setVisible(true);
    }

    private List<Graph> generateExampleGraphs(double startX, double endX) {
        double step = (endX - startX) / 1000;

        double[] xs = new double[1000];
        double[] ys = new double[1000];

        double x = startX;
        for (int i = 0; i < xs.length; i++, x += step) {
            xs[i] = x;
            ys[i] = x * x;
        }

        double[] xs1 = new double[1000];
        double[] ys1 = new double[1000];

        x = startX;
        for (int i = 0; i < xs.length; i++, x += step) {
            xs1[i] = x;
            ys1[i] = x * x * x;
        }

        double[] xs2 = new double[1000];
        double[] ys2 = new double[1000];

        x = startX;
        for (int i = 0; i < xs.length; i++, x += step) {
            xs2[i] = x;
            ys2[i] = Math.sqrt(x);
        }

        double[] xs3 = new double[] { 1, -1, -1, 1, 1 };
        double[] ys3 = new double[] { 1, 1, -1, -1, 1 };

        double[] xs4 = new double[] { 2 };
        double[] ys4 = new double[] { 2 };

        double[] xs5 = new double[] { -2 };
        double[] ys5 = new double[] { -2 };

        double[] xs6 = new double[1000];
        double[] ys6 = new double[1000];

        x = startX;
        for (int i = 0; i < xs.length; i++, x += step) {
            xs6[i] = x;
            ys6[i] = Math.pow(10, x);
        }

        return Arrays.asList(
                new Graph(xs, ys, Color.BLUE, "x^2"),
                new Graph(xs1, ys1, Color.RED, "x^3"),
                new Graph(xs2, ys2, Color.GREEN, "sqrt(x)"),
                new Graph(xs3, ys3, Color.PINK, "square"),

                new Graph(xs4, ys4, Color.PINK, "square"),
                new Graph(xs5, ys5, Color.PINK, "square"),
                new Graph(xs6, ys6, Color.YELLOW, "10^x")
        );
    }
}
