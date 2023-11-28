package ru.reosfire.special.model;

import java.awt.*;

public abstract class Atom {
    public Position position;

    public Atom(Position position) {
        this.position = position;
    }

    public abstract void paintTo(Graphics2D canvas, boolean selected, double factor);
    public abstract boolean intersect(Position position, double factor);
}
