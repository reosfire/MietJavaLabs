package ru.reosfire.special.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

public class MainFrame extends JFrame {
    private final static int HEIGHT = 1080;
    private final static int WIDTH = 1920;

    private final static int COUNT = 10;


    private final List<Button> buttons = new ArrayList<>();
    private final List<Thread> threads = new ArrayList<>();
    private volatile boolean isRunning = false;
    private volatile Color background = Color.LIGHT_GRAY;

    public MainFrame() throws HeadlessException {
        getRootPane().setBackground(Color.CYAN);
        setSize(WIDTH, HEIGHT);
        setLayout(null);

        Button restartButton = new Button("RESTART");

        getRootPane().addComponentListener(new ComponentAdapter()
        {
            public void componentResized(ComponentEvent evt) {
                Component c = (Component)evt.getSource();
                restartButton.setBounds(getWidth() / 2 - 100, getHeight() - 100, 200, 50);
            }
        });

        restartButton.addActionListener(it -> restartClicked());
        add(restartButton);


        setBackground(Color.CYAN);
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(background);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(background.darker());
        g.drawLine(getWidth() - 100, 0, getWidth() - 100, getHeight());
    }

    private void restartClicked() {
        for (Button button : buttons) {
            remove(button);
        }
        buttons.clear();

        for (Thread thread : threads) {
            thread.interrupt();
        }
        buttons.clear();

        isRunning = true;

        for (int i = 0; i < COUNT; i++) {
            Button button = new Button(Integer.toString(i));
            Color color = randomColor();
            AtomicReference<Double> speed = new AtomicReference<>(randomSpeed());

            int yPos = i * (getHeight() - 100) / (COUNT);

            button.setBackground(color);
            button.setForeground(color.darker());
            button.setBounds(100, yPos, 100, 20);

            AtomicReference<Double> position = new AtomicReference<>(0.0);
            int finalI = i;
            Thread thread = new Thread(() -> {
                while (isRunning) {
                    position.updateAndGet(v -> v + speed.get());

                    button.setBounds(100 + (int)Math.round(position.get()), yPos, 100, 20);

                    if (position.get() + 300 >= getWidth() && isRunning) {
                        background = color;
                        isRunning = false;
                        repaint();
                        makeDialog("Winner", "Racer number " + finalI + " has won this race");
                    }

                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException ignored) {

                    }
                }
            });
            threads.add(thread);
            thread.start();


            add(button);
            buttons.add(button);
            threads.add(thread);
        }
    }


    private double randomSpeed() {
        return ThreadLocalRandom.current().nextDouble(0.2, 2);
    }
    private Color randomColor() {
        Random rnd = ThreadLocalRandom.current();
        return new Color(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    private void makeDialog(String title, String content) {
        Dialog dialog = new Dialog(this, title, true);
        dialog.setLayout(new FlowLayout());

        Label label = new Label(content);
        label.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        dialog.add(label);

        Button restart = new Button("OK");
        restart.addActionListener(it -> dialog.setVisible(false));
        dialog.add(restart);

        dialog.setSize(350, 150);
        dialog.setVisible(true);
    }
}
