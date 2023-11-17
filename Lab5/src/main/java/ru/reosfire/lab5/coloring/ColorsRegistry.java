package ru.reosfire.lab5.coloring;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ColorsRegistry {

    private static final NamedColor[] POSSIBLE_COLORS = new NamedColor[] {
            new NamedColor(Color.RED, "RED"),
            new NamedColor(Color.GREEN,"GREEN"),
            new NamedColor(Color.BLUE,"BLUE"),
            new NamedColor(Color.MAGENTA,"MAGENTA"),
            new NamedColor(Color.ORANGE,"ORANGE"),
            new NamedColor(Color.CYAN,"CYAN"),
            new NamedColor(Color.PINK,"PINK"),
            new NamedColor(Color.YELLOW,"YELLOW"),
            new NamedColor(Color.DARK_GRAY,"DARK_GRAY"),
    };

    private final Set<Color> usedColors = new HashSet<>();
    private final List<Runnable> onChangedListeners = new ArrayList<>();


    public NamedColor getColorByName(String name) {
        for (NamedColor possibleColor : POSSIBLE_COLORS) {
            if (possibleColor.name.equals(name)) return possibleColor;
        }
        throw new RuntimeException("No such color");
    }

    public List<NamedColor> getAllUnusedColors() {
        ArrayList<NamedColor> result = new ArrayList<>();
        for (NamedColor possibleColor : POSSIBLE_COLORS) {
            if (usedColors.contains(possibleColor.color)) continue;
            result.add(possibleColor);
        }

        return result;
    }

    public void markAsUsed(Color color) {
        usedColors.add(color);
        notifyListeners();
    }

    public void markAsUnused(Color color) {
        usedColors.remove(color);
        notifyListeners();
    }

    public void markAllUnused() {
        usedColors.clear();
        notifyListeners();
    }

    public void addOnChangeListener(Runnable listener) {
        onChangedListeners.add(listener);
    }

    private void notifyListeners() {
        for (Runnable onChangedListener : onChangedListeners) {
            onChangedListener.run();
        }
    }
}
