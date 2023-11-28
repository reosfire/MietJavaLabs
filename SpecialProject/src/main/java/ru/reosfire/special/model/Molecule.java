package ru.reosfire.special.model;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Molecule {
    public final List<Atom> atoms;
    public final List<Edge> edges;

    public Molecule(List<Atom> atoms, List<Edge> edges) {
        this.atoms = atoms;
        this.edges = edges;
    }

    public Molecule() {
        atoms = new ArrayList<>();
        edges = new ArrayList<>();
    }


    public void addAtom(Atom atom) {
        atoms.add(atom);
    }

    public void removeAtom(Atom atom) {
        atoms.remove(atom);
        edges.removeIf(it -> it.end == atom || it.start == atom);
    }

    public void addEdge(Edge newEdge) {
        for (Edge edge : edges) {
            if (!edge.isSameAs(newEdge)) continue;

            edge.weight++;
            return;
        }
        edges.add(newEdge);
    }

    public void addEdge(Atom start, Atom end) {
        addEdge(new Edge(start, end));
    }


    public void paintTo(Graphics2D canvas, Atom selected, double factor) {
        paintAdjacency(canvas, factor);
        paintAtoms(canvas, selected, factor);
    }

    private void paintAdjacency(Graphics2D canvas, double factor) {
        Stroke initialStroke = canvas.getStroke();

        for (Edge edge : edges) {
            paintEdge(canvas, edge, factor);
        }

        canvas.setStroke(initialStroke);
    }

    private void paintEdge(Graphics2D canvas, Edge edge, double factor) {
        canvas.setStroke(new BasicStroke((float)(16 / edge.weight * factor)));

        Position startPosition = edge.start.position;
        Position endPosition = edge.end.position;

        Position shift = endPosition.minus(startPosition).normalize().times(20.0 / edge.weight * factor).perpendicular();

        startPosition = startPosition.plus(shift.times((edge.weight - 1) / -2.0));
        endPosition = endPosition.plus(shift.times((edge.weight - 1) / -2.0));

        for (int i = 0; i < edge.weight; i++) {
            Position currentStart = startPosition.plus(shift.times((double)i));
            Position currentEnd = endPosition.plus(shift.times((double)i));
            canvas.drawLine(currentStart.getIntX(), currentStart.getIntY(), currentEnd.getIntX(), currentEnd.getIntY());
        }
    }

    private void paintAtoms(Graphics2D canvas, Atom selected, double factor) {
        for (int i = atoms.size() - 1; i >= 0; i--) {
            Atom atom = atoms.get(i);
            atom.paintTo(canvas, atom == selected, factor);
        }
    }


    public void saveToFile(File file) {

    }

    public static Molecule readFromFile(File file) {
        return new Molecule();
    }
}
