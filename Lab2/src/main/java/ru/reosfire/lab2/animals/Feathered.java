package ru.reosfire.lab2.animals;

import ru.reosfire.lab2.enclosures.Covered;

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
