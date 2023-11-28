package ru.reosfire.special.model;

import ru.reosfire.special.model.atoms.AtomType;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
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


    public void saveTo(FileWriter writer) throws IOException {
        writer.write(atoms.size() + "\n");

        for (Atom atom : atoms) {
            atom.serialize(writer);
        }

        writer.write(edges.size() + "\n");

        for (Edge edge : edges) {
            writer.write(indexOfAtom(edge.start) + " " + indexOfAtom(edge.end) + " " + edge.weight + "\n");
        }
    }

    private int indexOfAtom(Atom atom) {
        for (int i = 0; i < atoms.size(); i++) {
            if (atoms.get(i) == atom) return i;
        }
        return -1;
    }

    public static Molecule readFrom(BufferedReader reader) throws IOException {
        Molecule result = new Molecule();

        int n = Integer.parseInt(reader.readLine());

        for (int i = 0; i < n; i++) {
            AtomType type = AtomType.valueOf(reader.readLine());

            String[] positionLine = reader.readLine().split(" ");
            double positionX = Double.parseDouble(positionLine[0]);
            double positionY = Double.parseDouble(positionLine[1]);

            Position position = new Position(positionX, positionY);

            result.addAtom(AtomsFactory.create(type, position));
        }

        int m = Integer.parseInt(reader.readLine());

        for (int i = 0; i < m; i++) {
            String[] positionLine = reader.readLine().split(" ");
            int start = Integer.parseInt(positionLine[0]);
            int end = Integer.parseInt(positionLine[1]);
            int weight = Integer.parseInt(positionLine[2]);

            result.addEdge(new Edge(result.atoms.get(start), result.atoms.get(end), weight));
        }

        return result;
    }
}
