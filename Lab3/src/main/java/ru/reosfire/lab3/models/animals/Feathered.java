package ru.reosfire.lab3.models.animals;

import ru.reosfire.lab3.models.enclosures.Covered;

import java.time.Duration;

public class Feathered extends Animal {
    private final Covered container;
    public Feathered(double weight, Duration lifeTime, Covered container) {
        super(weight, lifeTime);
        this.container = container;
    }

    @Override
    public void Move() {
        container.add(this);
    }
}
