package ru.reosfire.lab3.models.animals;

import ru.reosfire.lab3.models.enclosures.Enclosure;

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
