package ru.reosfire.lab5.components;

import ru.reosfire.lab5.foudation.Graph;
import ru.reosfire.lab5.foudation.MeasurementResult;
import ru.reosfire.lab5.foudation.Point;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Plotter extends JComponent {
    private List<Graph> currentContent;
    @Override
    protected void paintComponent(Graphics g) {
        if (!(g instanceof Graphics2D)) throw new RuntimeException("graphics is not graphics2d");
        Graphics2D canvas = (Graphics2D) g;
        configureCanvas(canvas);

        MeasurementResult measurementResult = remeasure();
        drawBackground(canvas, measurementResult);
        drawGraphs(canvas, measurementResult);
        drawLegend(canvas, measurementResult);
    }

    public void setContent(List<Graph> content) {
        currentContent = content;
        repaint();
    }

    private MeasurementResult remeasure() {
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

        double xShift = getWidth() / 2;
        double yShift = 1000;
        double xFactor = -getWidth() / (width + 1);
        double yFactor = -getWidth() / (height + 1);

        return new MeasurementResult(xShift, yShift, xFactor, yFactor);
    }

    private void drawBackground(Graphics2D canvas, MeasurementResult measurementResult) {

    }

    private void drawGraphs(Graphics2D canvas, MeasurementResult measurementResult) {
        if (currentContent == null) return;
        for (Graph graph : currentContent) {
            drawGraph(graph, canvas, measurementResult);
        }
    }

    private void drawLegend(Graphics2D canvas, MeasurementResult measurementResult) {

    }

    private void drawGraph(Graph graph, Graphics2D canvas, MeasurementResult measurementResult) {
        canvas.setColor(graph.getColor());
        canvas.setStroke(new BasicStroke(10));

        Point prev = graph.get(0);
        for (int i = 1; i < graph.size(); i++) {
            Point curr = graph.get(i);
            int x1 = (int)Math.round(prev.x * measurementResult.xFactor + measurementResult.xShift);
            int y1 = (int)Math.round(prev.y * measurementResult.yFactor + measurementResult.yShift);
            int x2 = (int)Math.round(curr.x * measurementResult.xFactor + measurementResult.xShift);
            int y2 = (int)Math.round(curr.y * measurementResult.yFactor + measurementResult.yShift);

            canvas.drawLine(x1, y1, x2, y2);

            prev = curr;
        }
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
