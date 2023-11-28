package ru.reosfire.special.model;

import ru.reosfire.special.model.atoms.AtomType;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Atom {
    public Position position;
    public final AtomType type;

    public Atom(Position position, AtomType type) {
        this.position = position;
        this.type = type;
    }

    public abstract void paintTo(Graphics2D canvas, boolean selected, double factor);
    public abstract boolean intersect(Position position, double factor);
    public void serialize(FileWriter writer) throws IOException {
        writer.write(type.name() + "\n");
        writer.write(position.x + " " + position.y + "\n");
    }
}
