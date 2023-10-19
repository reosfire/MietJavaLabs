package ru.reosfire.lab3.models.enclosures;

import ru.reosfire.lab3.models.animals.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Enclosure<T extends Animal<T>> {
    protected List<T> animals = new ArrayList<>();

    public void add(T animal) {
        animals.add(animal);
    }

    @Override
    public String toString() {
        return animals.stream().map(Animal::toString).collect(Collectors.joining("\n")) + "\n";
    }
}
