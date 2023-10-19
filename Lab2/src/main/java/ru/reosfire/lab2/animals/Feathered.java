package ru.reosfire.lab2.animals;

import ru.reosfire.lab2.enclosures.Enclosure;

import java.time.Duration;

public class Feathered extends Animal<Feathered> {
    public Feathered(double weight, Duration lifeTime) {
        super(weight, lifeTime);
    }

    @Override
    public void Move(Enclosure<Feathered> container) {
        container.add(this);
    }
}
