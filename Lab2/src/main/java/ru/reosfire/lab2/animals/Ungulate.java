package ru.reosfire.lab2.animals;

import ru.reosfire.lab2.enclosures.Opened;

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
