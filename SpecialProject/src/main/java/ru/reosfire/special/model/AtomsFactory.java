package ru.reosfire.special.model;

import ru.reosfire.special.model.atoms.AtomType;
import ru.reosfire.special.model.atoms.RoundAtom;
import ru.reosfire.special.model.atoms.StringAtom;

import java.awt.*;

public class AtomsFactory {
    public static Atom create(AtomType type, Position position) {
        switch (type) {
            case H:
                return new RoundAtom(position, Color.CYAN, 30, AtomType.H);
            case N:
                return new RoundAtom(position, Color.MAGENTA, 40, AtomType.N);
            case C:
                return new RoundAtom(position, Color.YELLOW, 40, AtomType.C);
            case O:
                return new RoundAtom(position, Color.BLUE, 50, AtomType.O);
            case R:
                return new StringAtom(position, "R", AtomType.R);
        }
        throw new RuntimeException("Unknown atom type in factory");
    }
}
