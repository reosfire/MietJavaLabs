package ru.reosfire.lab3.models.animals;

import ru.reosfire.lab3.models.enclosures.Opened;

import java.time.Duration;

public class Ungulate extends Animal {
    private final Opened container;
    public Ungulate(double weight, Duration lifeTime, Opened container) {
        super(weight, lifeTime);
        this.container = container;
    }

    @Override
    public void Move() {
        container.add(this);
    }
}
