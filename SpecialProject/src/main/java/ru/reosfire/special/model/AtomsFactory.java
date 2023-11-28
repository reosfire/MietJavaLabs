package ru.reosfire.special.model;

import ru.reosfire.special.model.atoms.AtomType;
import ru.reosfire.special.model.atoms.RoundAtom;
import ru.reosfire.special.model.atoms.StringAtom;

import java.awt.*;

public class AtomsFactory {
    public Atom create(AtomType type, Position position) {
        switch (type) {
            case H:
                return new RoundAtom(position, Color.CYAN, 30);
            case N:
                return new RoundAtom(position, Color.MAGENTA, 40);
            case C:
                return new RoundAtom(position, Color.YELLOW, 40);
            case O:
                return new RoundAtom(position, Color.BLUE, 50);
            case R:
                return new StringAtom(position, "R");
        }
        throw new RuntimeException("Unknown atom type in factory");
    }
}
