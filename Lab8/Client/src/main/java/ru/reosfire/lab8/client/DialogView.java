package ru.reosfire.lab8.client;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class DialogView extends JComponent {
    private static final int FONT_SIZE = 20;
    private static final Font MESSAGE_FONT = new Font("TimesRoman", Font.PLAIN, FONT_SIZE);
    private static final Font SENDER_FONT = new Font("TimesRoman", Font.BOLD, FONT_SIZE);
    private static final Color MESSAGE_BACKGROUND_COLOR = new Color(0, 136, 204);

    private volatile double upperScrollBound = 0;
    private volatile double scroll = 0;
    private final Object scrollLock = new Object();

    private List<Message> messages = new ArrayList<>();
    private String senderName;

    public DialogView() {
        addMouseWheelListener((it) -> {
            AtomicReference<Double> scrollAmount = new AtomicReference<>(it.getPreciseWheelRotation() * 100);
            double delta = scrollAmount.get() / 20;

            new Thread(() -> {
                while (Math.abs(scrollAmount.get()) > 0.1) {
                    scrollAmount.updateAndGet(v -> v - delta);

                    synchronized (scrollLock) {
                        scroll -= delta;
                        scroll = Math.min(scroll, upperScrollBound);
                        scroll = Math.max(scroll, 0);
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
        try {
            if (!(g instanceof Graphics2D)) throw new RuntimeException("graphics is not graphics2d");
            Graphics2D canvas = (Graphics2D) g;
            configureCanvas(canvas);
            canvas.setFont(MESSAGE_FONT);

            double scroll;
            synchronized (scrollLock) {
                scroll = this.scroll;
                scroll = Math.min(scroll, upperScrollBound);
                scroll = Math.max(scroll, 0);
            }

            int prev = toInt(getHeight() - 10 + scroll);
            for (int i = messages.size() - 1; i >= 0; i--) {
                Message message = messages.get(i);
                if (senderName.equals(message.senderName)) {
                    prev = drawRightMessage(canvas, message, prev - 10);
                } else {
                    prev = drawLeftMessage(canvas, message, prev - 10);
                }
            }

            synchronized (scrollLock) {
                upperScrollBound = scroll - prev + 10;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    public String getSenderName() {
        return senderName;
    }
    public void setContent(List<Message> messages) {
        this.messages = messages;
        repaint();
    }

    private int drawRightMessage(Graphics2D canvas, Message message, int y) {
        int maxWidth = getWidth() * 3 / 4;
        Rectangle2D labelBounds = new TextLayout(message.content, MESSAGE_FONT, canvas.getFontRenderContext()).getBounds();

        List<String> senderLines = getStrings(canvas.getFontRenderContext(), SENDER_FONT, message.senderName, maxWidth);
        List<String> messageLines = getStrings(canvas.getFontRenderContext(), MESSAGE_FONT, message.content, maxWidth);

        int lineHeight = (int)(labelBounds.getHeight() + 20);

        int width = Math.max(getMaxLineWidth(canvas.getFontRenderContext(), SENDER_FONT, senderLines),
                getMaxLineWidth(canvas.getFontRenderContext(), MESSAGE_FONT, messageLines)) + 20;
        int height = (senderLines.size() + messageLines.size()) * lineHeight + 5;

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
        canvas.setColor(new Color(210, 210, 210));
        canvas.setFont(SENDER_FONT);

        int lineY = upperCornerY + 25;
        for (String senderLine : senderLines) {
            canvas.drawString(senderLine, getWidth() - width, lineY);
            lineY += lineHeight;
        }

        canvas.setColor(Color.WHITE);
        canvas.setFont(MESSAGE_FONT);
        for (String messageLine : messageLines) {
            canvas.drawString(messageLine, getWidth() - width, lineY);
            lineY += lineHeight;
        }

        return upperCornerY;
    }

    private int drawLeftMessage(Graphics2D canvas, Message message, int y) {
        int maxWidth = getWidth() * 3 / 4;
        Rectangle2D labelBounds = new TextLayout(message.content, MESSAGE_FONT, canvas.getFontRenderContext()).getBounds();

        List<String> senderLines = getStrings(canvas.getFontRenderContext(), SENDER_FONT, message.senderName, maxWidth);
        List<String> messageLines = getStrings(canvas.getFontRenderContext(), MESSAGE_FONT, message.content, maxWidth);

        int lineHeight = (int)(labelBounds.getHeight() + 20);

        int width = Math.max(getMaxLineWidth(canvas.getFontRenderContext(), SENDER_FONT, senderLines),
                getMaxLineWidth(canvas.getFontRenderContext(), MESSAGE_FONT, messageLines)) + 20;
        int height = (senderLines.size() + messageLines.size()) * lineHeight + 5;

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
        canvas.setColor(new Color(210, 210, 210));
        canvas.setFont(SENDER_FONT);

        int lineY = upperCornerY + 25;
        for (String senderLine : senderLines) {
            canvas.drawString(senderLine, 20, lineY);
            lineY += lineHeight;
        }

        canvas.setColor(Color.WHITE);
        canvas.setFont(MESSAGE_FONT);
        for (String messageLine : messageLines) {
            canvas.drawString(messageLine, 20, lineY);
            lineY += lineHeight;
        }

        return upperCornerY;
    }

    private static final Map<String, List<String>> cache = new HashMap<>();
    private static List<String> getStrings(FontRenderContext context, Font font, String text, int maxWidth) {
        List<String> cached = cache.get(text + maxWidth);
        if (cached != null) return cached;


        List<String> messageLines = new ArrayList<>();
        String current = "";
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            String next = current + c;

            Rectangle2D nextBounds = new TextLayout(next, font, context).getBounds();
            if (nextBounds.getWidth() + 20 > maxWidth) {
                messageLines.add(current);
                current = "";
            }
            current += c;
        }

        if (!current.isEmpty()) messageLines.add(current);

        cache.put(text + maxWidth, messageLines);
        return messageLines;
    }

    private static int getMaxLineWidth(FontRenderContext context, Font font, List<String> lines) {
        int width = 0;
        for (String messageLine : lines) {
            Rectangle2D lineBounds = new TextLayout(messageLine, font, context).getBounds();
            width = Math.max(width, toInt(lineBounds.getWidth()));
        }

        return width;
    }

    private static int toInt(double value) {
        return (int) Math.round(value);
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
