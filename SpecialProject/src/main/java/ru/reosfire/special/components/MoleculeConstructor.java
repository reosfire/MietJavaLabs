package ru.reosfire.special.components;

import ru.reosfire.special.event.listeners.DragEventHandler;
import ru.reosfire.special.event.listeners.PressReleaseHandler;
import ru.reosfire.special.model.Atom;
import ru.reosfire.special.model.AtomsFactory;
import ru.reosfire.special.model.Molecule;
import ru.reosfire.special.model.Position;
import ru.reosfire.special.model.atoms.AtomType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

public class MoleculeConstructor extends JComponent {
    private final int MENU_LINE_HEIGHT = 120;
    private final int MENU_WIDTH = 200;

    private Molecule state = new Molecule();
    private Atom freeAtom;

    private Atom selectedAtom;

    private volatile double factor = 1;
    private final Object scrollLock = new Object();

    private final AtomsFactory atomsFactory = new AtomsFactory();

    @Deprecated()
    public void initState() {
        for (int i = 0; i < 20; i++) {
            state.atoms.add(atomsFactory.create(AtomType.H, randomPosition()));
        }
        for (int i = 0; i < 20; i++) {
            state.atoms.add(atomsFactory.create(AtomType.N, randomPosition()));
        }
        for (int i = 0; i < 20; i++) {
            state.atoms.add(atomsFactory.create(AtomType.C, randomPosition()));
        }
        for (int i = 0; i < 20; i++) {
            state.atoms.add(atomsFactory.create(AtomType.O, randomPosition()));
        }
        for (int i = 0; i < 20; i++) {
            state.atoms.add(atomsFactory.create(AtomType.R, randomPosition()));
        }

        for (int i = 0; i < 500; i++) {
            int i1 = ThreadLocalRandom.current().nextInt(100);
            int i2 = ThreadLocalRandom.current().nextInt(100);
            if (i1 == i2) continue;
            state.addEdge(state.atoms.get(i1), state.atoms.get(i2));
        }
    }

    public MoleculeConstructor() {
        initState();

        final Atom[] movingAtom = { null };
        final Position[] activeAtomShift = { null };
        final int[] dragged = { 0 };
        addMouseListener(new PressReleaseHandler() {
            @Override
            public void mousePressed(MouseEvent e) {
                Position position = new Position(e.getX(), e.getY());

                if (intersectMenu(position)) {
                    int menuIndex = getMenuIndex(position);
                    freeAtom = atomsFactory.create(AtomType.values()[menuIndex], position);
                } else {
                    dragged[0] = 0;

                    for (Atom atom : state.atoms) {
                        if (atom.intersect(position, factor)) {
                            movingAtom[0] = atom;
                            activeAtomShift[0] = atom.position.minus(position);
                            return;
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Position position = new Position(e.getX(), e.getY());

                if (intersectMenu(position)) {
                    freeAtom = null;
                } else if (freeAtom != null) {
                    state.addAtom(freeAtom);
                    freeAtom = null;
                } else {
                    if (dragged[0] < 5) {
                        if (movingAtom[0] != null) {
                            if (movingAtom[0] == selectedAtom) {
                                selectedAtom = null;
                            } else {
                                if (selectedAtom == null) {
                                    selectedAtom = movingAtom[0];
                                } else {
                                    state.addEdge(selectedAtom, movingAtom[0]);
                                    selectedAtom = null;
                                }
                            }
                        } else {
                            selectedAtom = null;
                        }
                    }
                    movingAtom[0] = null;
                    dragged[0] = 0;
                }

                repaint();
            }
        });

        addMouseMotionListener((DragEventHandler) it -> {
            Position position = new Position(it.getX(), it.getY());
            if (outOfComponent(position)) return;

            if (freeAtom != null) {
                freeAtom.position = position;
                repaint();
            } else {
                if (movingAtom[0] != null) {
                    movingAtom[0].position = position.plus(activeAtomShift[0]);
                    dragged[0]++;
                    repaint();
                }
            }
        });

        addMouseWheelListener((it) -> {
            AtomicReference<Double> scrollAmount = new AtomicReference<>(it.getPreciseWheelRotation() * -0.25);
            double delta = scrollAmount.get() / 20;

            new Thread(() -> {
                while (Math.abs(scrollAmount.get()) > 0.1) {
                    scrollAmount.updateAndGet(v -> v - delta);

                    synchronized (scrollLock) {
                        double newFactor = factor * (delta + 1);
                        if (newFactor < 0.25 || newFactor > 4) return;
                        factor = newFactor;


                        for (Atom atom : state.atoms) {
                            atom.position = atom.position.updateZoom(new Position(it.getX(), it.getY()), delta + 1);
                        }
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

        registerKeyboardAction(e -> {
            if (selectedAtom != null) {
                state.removeAtom(selectedAtom);
                selectedAtom = null;
                repaint();
            }
        }, KeyStroke.getKeyStroke("DELETE"), WHEN_IN_FOCUSED_WINDOW);
    }

    public void setState(Molecule molecule) {
        state = molecule;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (!(g instanceof Graphics2D)) throw new RuntimeException("graphics is not graphics2d");
        Graphics2D canvas = (Graphics2D) g;
        configureCanvas(canvas);

        state.paintTo(canvas, selectedAtom, factor);
        paintMenu(canvas);

        if (freeAtom != null) {
            freeAtom.paintTo(canvas, true, factor);
        }
    }

    private boolean intersectMenu(Position position) {
        return position.y >= 0 && position.y <= getMenuHeight() && position.x <= getWidth() && position.x >= getWidth() - MENU_WIDTH;
    }

    private void paintMenu(Graphics2D canvas) {
        canvas.setColor(new Color(150, 150, 150, 150));
        canvas.fillRoundRect(getWidth() - MENU_WIDTH, 0, MENU_WIDTH, getMenuHeight(), 20, 20);

        int currentY = 60;
        AtomType[] atomTypes = AtomType.values();

        for (int i = 0; i < atomTypes.length; i++) {
            Atom atom = atomsFactory.create(atomTypes[i], new Position(getWidth() - MENU_WIDTH + 60, currentY));
            atom.paintTo(canvas, false, 1);

            canvas.setColor(Color.BLACK);
            canvas.setFont(new Font("TimesRoman", Font.PLAIN, 45));
            canvas.drawString(atomTypes[i].name(), getWidth() - MENU_WIDTH + 130, currentY + 15);

            currentY += MENU_LINE_HEIGHT;

            canvas.setStroke(new BasicStroke(1));
            if (i < atomTypes.length - 1) {
                canvas.drawLine(getWidth() - MENU_WIDTH + 1, currentY - 60, getWidth(), currentY - 60);
            }
        }
    }

    private int getMenuIndex(Position position) {
        return Math.min(((int)position.y) / MENU_LINE_HEIGHT, AtomType.values().length - 1);
    }

    private int getMenuHeight() {
        return MENU_LINE_HEIGHT * AtomType.values().length;
    }

    private boolean outOfComponent(Position position) {
        return position.x < 0 || position.x > getWidth() || position.y < 0 || position.y > getHeight();
    }

    private int randomRadius() {
        Random rnd = ThreadLocalRandom.current();
        return rnd.nextInt(50) + 10;
    }

    private Color randomColor() {
        Random rnd = ThreadLocalRandom.current();
        return new Color(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    private Position randomPosition() {
        Random rnd = ThreadLocalRandom.current();
        return new Position(rnd.nextInt(1000), rnd.nextInt(1000));
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
