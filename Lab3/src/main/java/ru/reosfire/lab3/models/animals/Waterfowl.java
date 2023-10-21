package ru.reosfire.lab3.models.animals;

import ru.reosfire.lab3.models.enclosures.Aquarium;

import java.time.Duration;

public class Waterfowl extends Animal {
    private final Aquarium container;
    public Waterfowl(double weight, Duration lifeTime, Aquarium container) {
        super(weight, lifeTime);
        this.container = container;
    }

    @Override
    public void Move() {
        container.add(this);
    }
}
