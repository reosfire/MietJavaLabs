package ru.reosfire.lab3.models.animals;

import ru.reosfire.lab3.models.enclosures.Terrarium;

import java.time.Duration;

public class ColdBlooded extends Animal {
    private final Terrarium container;
    public ColdBlooded(double weight, Duration lifeTime, Terrarium container) {
        super(weight, lifeTime);
        this.container = container;
    }

    @Override
    public void Move() {
        container.add(this);
    }
}
