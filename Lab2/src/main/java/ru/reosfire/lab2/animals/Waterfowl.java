package ru.reosfire.lab2.animals;

import ru.reosfire.lab2.enclosures.Enclosure;

import java.time.Duration;

public class Waterfowl extends Animal<Waterfowl> {

    public Waterfowl(double weight, Duration lifeTime) {
        super(weight, lifeTime);
    }

    @Override
    public void Move(Enclosure<Waterfowl> container) {
        container.add(this);
    }
}
