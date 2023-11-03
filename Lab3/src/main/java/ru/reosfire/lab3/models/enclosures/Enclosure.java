package ru.reosfire.lab3.models.enclosures;

import ru.reosfire.lab3.models.animals.Animal;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Enclosure<T extends Animal> {
    protected List<T> animals = new ArrayList<>();

    public void add(T animal) {
        animals.add(animal);
    }

    @Override
    public String toString() {
        if (animals.isEmpty()) return "Empty";
        return animals.stream().map(Animal::toString).collect(Collectors.joining("\n")) + "\n";
    }

    public abstract void serialize(OutputStreamWriter writer) throws IOException;
}
