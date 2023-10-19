package ru.reosfire.lab2.animals;

import ru.reosfire.lab2.enclosures.Enclosure;

import java.time.Duration;

public class Ungulates extends Animal<Ungulates> {
    public Ungulates(double weight, Duration lifeTime) {
        super(weight, lifeTime);
    }

    @Override
    public void Move(Enclosure<Ungulates> container) {
        container.add(this);
    }
}
