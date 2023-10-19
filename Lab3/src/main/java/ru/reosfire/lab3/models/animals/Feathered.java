package ru.reosfire.lab3.models.animals;

import ru.reosfire.lab3.models.enclosures.Enclosure;

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
