package ru.reosfire.lab3.models.animals;

import ru.reosfire.lab3.models.enclosures.Enclosure;

import java.time.Duration;

public class ColdBlooded extends Animal<ColdBlooded> {
    public ColdBlooded(double weight, Duration lifeTime) {
        super(weight, lifeTime);
    }

    @Override
    public void Move(Enclosure<ColdBlooded> container) {
        container.add(this);
    }
}
