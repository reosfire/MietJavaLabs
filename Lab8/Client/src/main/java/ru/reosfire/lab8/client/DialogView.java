package ru.reosfire.lab8.client;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DialogView extends JComponent {
    private static final int FONT_SIZE = 20;
    private static final Font FONT = new Font("TimesRoman", Font.PLAIN, FONT_SIZE);
    private static final Color MESSAGE_BACKGROUND_COLOR = new Color(0, 136, 204);

    private volatile double scroll = 0;
    private final Object scrollLock = new Object();

    public DialogView() {
        addMouseWheelListener((it) -> {
            AtomicReference<Double> scrollAmount = new AtomicReference<>(it.getPreciseWheelRotation() * 100);
            double delta = scrollAmount.get() / 20;

            new Thread(() -> {
                while (Math.abs(scrollAmount.get()) > 0.1) {
                    scrollAmount.updateAndGet(v -> v - delta);

                    synchronized (scrollLock) {
                        scroll += delta;
                    }

                    repaint();
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (!(g instanceof Graphics2D)) throw new RuntimeException("graphics is not graphics2d");
        Graphics2D canvas = (Graphics2D) g;
        configureCanvas(canvas);
        canvas.setFont(FONT);

        synchronized (scrollLock) {
            int prev = drawRightMessage(canvas, "asdfasdfasdfasdfasdfasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", toInt(getHeight() - 10 + scroll));
            prev = drawRightMessage(canvas, "asdfasdfasdfasdfasdfasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", prev - 10);
            drawLeftMessage(canvas, "asdfasdfasdfasdfasdfasdfasdfaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", prev - 10);
        }
    }



    private int drawRightMessage(Graphics2D canvas, String text, int y) {
        int maxWidth = getWidth() * 3 / 4;
        Rectangle2D labelBounds = new TextLayout(text, FONT, canvas.getFontRenderContext()).getBounds();

        List<String> messageLines = getStrings(canvas.getFontRenderContext(), text, maxWidth);

        int lineHeight = (int)(labelBounds.getHeight() + 20);

        int width = getMaxLineWidth(canvas.getFontRenderContext(), messageLines) + 20;
        int height = messageLines.size() * lineHeight;

        int upperCornerY = y - height;

        //draw background
        canvas.setColor(MESSAGE_BACKGROUND_COLOR);
        canvas.fillRoundRect(getWidth() - width - 10, upperCornerY, width, height, 20, 20);
        int[] xs = new int[] {
                getWidth(),
                getWidth() - 30,
                getWidth() - 30
        };
        int[] ys = new int[] {
                y,
                y,
                y - 20
        };
        canvas.fillPolygon(xs, ys, 3);


        //draw lines
        canvas.setColor(Color.WHITE);

        int firstLineY = upperCornerY + 25;
        for (int i = 0; i < messageLines.size(); i++) {
            canvas.drawString(messageLines.get(i), getWidth() - width, firstLineY + i * lineHeight);
        }

        //need to return some measurements results to understand from outside where to place next message
        return upperCornerY;
    }

    private int drawLeftMessage(Graphics2D canvas, String text, int y) {
        int maxWidth = getWidth() * 3 / 4;
        Rectangle2D labelBounds = new TextLayout(text, FONT, canvas.getFontRenderContext()).getBounds();

        List<String> messageLines = getStrings(canvas.getFontRenderContext(), text, maxWidth);

        int lineHeight = (int)(labelBounds.getHeight() + 20);

        int width = getMaxLineWidth(canvas.getFontRenderContext(), messageLines) + 20;
        int height = messageLines.size() * lineHeight;

        int upperCornerY = y - height;

        //draw background
        canvas.setColor(MESSAGE_BACKGROUND_COLOR);
        canvas.fillRoundRect(10, upperCornerY, width, height, 20, 20);
        int[] xs = new int[] {
                0,
                30,
                30
        };
        int[] ys = new int[] {
                y,
                y,
                y - 20
        };
        canvas.fillPolygon(xs, ys, 3);


        //draw lines
        canvas.setColor(Color.WHITE);

        int firstLineY = upperCornerY + 25;
        for (int i = 0; i < messageLines.size(); i++) {
            canvas.drawString(messageLines.get(i), 20, firstLineY + i * lineHeight);
        }

        //need to return some measurements results to understand from outside where to place next message
        return upperCornerY;
    }

    private static List<String> getStrings(FontRenderContext context, String text, int maxWidth) {
        List<String> messageLines = new ArrayList<>();
        String current = "";
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            String next = current + c;

            Rectangle2D nextBounds = new TextLayout(next, FONT, context).getBounds();
            if (nextBounds.getWidth() + 20 > maxWidth) {
                messageLines.add(current);
                current = "";
            }
            current += c;
        }

        if (!current.isEmpty()) messageLines.add(current);
        return messageLines;
    }

    private static int getMaxLineWidth(FontRenderContext context, List<String> lines) {
        int width = 0;
        for (String messageLine : lines) {
            Rectangle2D lineBounds = new TextLayout(messageLine, FONT, context).getBounds();
            width = Math.max(width, toInt(lineBounds.getWidth()));
        }

        return width;
    }

    private static int toInt(double value) {
        return (int)Math.round(value);
    }
    private static int toInt(float value) {
        return Math.round(value);
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
