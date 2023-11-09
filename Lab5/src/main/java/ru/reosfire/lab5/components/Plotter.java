package ru.reosfire.lab5.components;

import ru.reosfire.lab5.foudation.Graph;
import ru.reosfire.lab5.foudation.Measurement;
import ru.reosfire.lab5.foudation.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;

public class Plotter extends JComponent {
    private static final double ARROW_SIZE_FACTOR = 0.01;
    private static final int FONT_SIZE = 20;
    private static final Font FONT = new Font("TimesRoman", Font.PLAIN, FONT_SIZE);
    private static final int LEGEND_LINE_LENGTH = 100;
    private static final int LEGEND_ENTRY_HEIGHT = 30;


    private List<Graph> currentContent;
    @Override
    protected void paintComponent(Graphics g) {
        if (!(g instanceof Graphics2D)) throw new RuntimeException("graphics is not graphics2d");
        Graphics2D canvas = (Graphics2D) g;
        configureCanvas(canvas);

        Measurement measurement = remeasure();
        if (measurement == null) return;
        drawBackground(canvas, measurement);
        drawGraphs(canvas, measurement);
        drawLegend(canvas);
    }

    public void setContent(List<Graph> content) {
        currentContent = content;
        repaint();
    }

    private Measurement remeasure() {
        if (currentContent == null || currentContent.isEmpty()) return null;

        double maxX = currentContent.get(0).maxX();
        double maxY = currentContent.get(0).maxY();
        double minX = currentContent.get(0).minX();
        double minY = currentContent.get(0).minY();
        for (Graph graph : currentContent) {
            maxX = Math.max(maxX, graph.maxX());
            maxY = Math.max(maxY, graph.maxY());
            minX = Math.min(minX, graph.minX());
            minY = Math.min(minY, graph.minY());
        }

        double width = maxX - minX;
        double height = maxY - minY;

        double xFactor = getWidth() / (width);
        double yFactor = -getHeight() / (height);

        double xShift = -minX*xFactor;
        double yShift = -maxY*yFactor;

        return new Measurement(xShift, yShift, xFactor, yFactor, maxX, maxY, minX, minY);
    }

    private void drawBackground(Graphics2D canvas, Measurement measurement) {
        canvas.setColor(Color.BLACK);
        canvas.setStroke(new BasicStroke(3));
        canvas.setFont(FONT);

        drawHorizontalAxes(canvas, measurement, "x");
        drawVerticalAxes(canvas, measurement, "y");


        canvas.setColor(Color.BLACK);
        canvas.setStroke(new BasicStroke(0.6f));
        for (double x : measurement.getHorizontalGrid()) {
            drawVerticalLine(canvas, x, measurement);

            String label = Double.toString(x);
            canvas.drawString(label, measurement.xToPx(x) + 5, measurement.yToPx(0) + FONT_SIZE);
        }

        for (double y : measurement.getVerticalGrid()) {
            drawHorizontalLine(canvas, y, measurement);

            String label = Double.toString(y);
            canvas.drawString(label, measurement.xToPx(0) + 5, measurement.yToPx(y) - 5);
        }

        canvas.drawString("0", measurement.xToPx(0) + 5, measurement.yToPx(0) + FONT_SIZE);
    }

    private void drawGraphs(Graphics2D canvas, Measurement measurement) {
        for (Graph graph : currentContent) {
            drawGraph(graph, canvas, measurement);
        }
    }

    private void drawLegend(Graphics2D canvas) {
        int startY = (currentContent.size()) * LEGEND_ENTRY_HEIGHT;
        double maxWidth = currentContent.stream().mapToDouble(it -> FONT.getStringBounds(it.getLabel(), canvas.getFontRenderContext()).getWidth()).max().getAsDouble();
        int startX = (int)Math.round(maxWidth + 100 + 40);

        canvas.setColor(new Color(150, 150, 150, 100));
        int leftX = getWidth() - startX - 10;
        int leftY = getHeight() - startY - 30;
        int rightX = getWidth() - 5;
        int rightY = getHeight() - 5;
        canvas.fillRoundRect(leftX, leftY, rightX - leftX, rightY - leftY, 20, 20);

        canvas.setFont(new Font("TimesRoman", Font.BOLD, FONT_SIZE));
        for (int i = 0; i < currentContent.size(); i++) {
            Graph graph = currentContent.get(i);
            drawLegendEntry(canvas, graph, getWidth() - startX, getHeight() - startY + i * LEGEND_ENTRY_HEIGHT);
        }
    }


    private void drawLegendEntry(Graphics2D canvas, Graph graph, int x, int y) {
        canvas.setColor(Color.BLACK);
        canvas.setStroke(new BasicStroke(10));

        Rectangle2D labelBounds = FONT.getStringBounds(graph.getLabel(), canvas.getFontRenderContext());
        canvas.drawString(graph.getLabel(), x, y);

        canvas.setColor(graph.getColor());
        canvas.setStroke(new BasicStroke(10));

        int lineStartX = (int)Math.round(x + labelBounds.getWidth());
        canvas.drawLine(lineStartX + 20, y - 5, lineStartX + LEGEND_LINE_LENGTH + 20, y - 5);
    }

    private void drawGraph(Graph graph, Graphics2D canvas, Measurement measurement) {
        canvas.setColor(graph.getColor());
        canvas.setStroke(new BasicStroke(4));

        Point prev = graph.get(0);
        for (int i = 1; i < graph.size(); i++) {
            Point curr = graph.get(i);
            int x1 = measurement.xToPx(prev.x);
            int y1 = measurement.yToPx(prev.y);
            int x2 = measurement.xToPx(prev.x);
            int y2 = measurement.yToPx(prev.y);

            canvas.drawLine(x1, y1, x2, y2);

            prev = curr;
        }
    }

    private void drawHorizontalAxes(Graphics2D canvas, Measurement measurement, String label) {
        int yPx = measurement.yToPx(0);
        drawHorizontalLinePx(canvas, yPx);

        int arrowStart = (int)Math.round(getWidth() * (1 - ARROW_SIZE_FACTOR));
        int arrowVerticalShift = (int)Math.round(getHeight() * ARROW_SIZE_FACTOR * 0.5);

        canvas.drawLine(getWidth(), yPx, arrowStart, yPx + arrowVerticalShift);
        canvas.drawLine(getWidth(), yPx, arrowStart, yPx - arrowVerticalShift);

        canvas.drawString(label, arrowStart - 10, yPx + arrowVerticalShift + 15);
    }
    private void drawVerticalAxes(Graphics2D canvas, Measurement measurement, String label) {
        int xPx = measurement.xToPx(0);
        drawVerticalLinePx(canvas, xPx);

        int arrowStart = (int)Math.round(getHeight() * ARROW_SIZE_FACTOR);
        int arrowHorizontalShift = (int)Math.round(getWidth() * ARROW_SIZE_FACTOR * 0.5);

        canvas.drawLine(xPx, 0, xPx - arrowHorizontalShift, arrowStart);
        canvas.drawLine(xPx, 0, xPx + arrowHorizontalShift, arrowStart);

        canvas.drawString(label, xPx + arrowHorizontalShift + 5, arrowStart + 15);
    }

    private void drawHorizontalLine(Graphics2D canvas, double y, Measurement measurement) {
        drawHorizontalLinePx(canvas, measurement.yToPx(y));
    }
    private void drawVerticalLine(Graphics2D canvas, double x, Measurement measurement) {
        drawVerticalLinePx(canvas, measurement.xToPx(x));
    }
    private void drawHorizontalLinePx(Graphics2D canvas, int yPx) {
        canvas.drawLine(0, yPx, getWidth(), yPx);
    }
    private void drawVerticalLinePx(Graphics2D canvas, int xPx) {
        canvas.drawLine(xPx, 0, xPx, getHeight());
    }

    private static void configureCanvas(Graphics2D canvas) {
        canvas.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        canvas.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        canvas.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        canvas.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        canvas.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        canvas.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        canvas.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }
}
