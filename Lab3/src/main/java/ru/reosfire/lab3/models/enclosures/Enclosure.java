package ru.reosfire.lab3.models.enclosures;

import ru.reosfire.lab3.models.animals.Animal;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Enclosure<T extends Animal> {
    protected List<T> animals = new ArrayList<>();

    public void add(T animal) {
        animals.add(animal);
    }

    public void removeById(int id) {
        int index = -1;
        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).Id == id) {
                index = i;
                break;
            }
        }
        if (index < 0) throw new RuntimeException("Animal with id: " + id + " not found");
        animals.remove(index);
    }

    public void updateWeight(int id, double weight) {
        getById(id).Weight = weight;
    }

    public void updateLifetime(int id, Duration lifetime) {
        getById(id).LifeTime = lifetime;
    }

    private Animal getById(int id) {
        int index = -1;
        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).Id == id) {
                index = i;
                break;
            }
        }
        if (index < 0) throw new RuntimeException("Animal with id: " + id + " not found");
        return animals.get(index);
    }

    @Override
    public String toString() {
        if (animals.isEmpty()) return "Empty";
        return animals.stream().map(Animal::toString).collect(Collectors.joining("\n")) + "\n";
    }

    public abstract void serialize(OutputStreamWriter writer) throws IOException;
}
