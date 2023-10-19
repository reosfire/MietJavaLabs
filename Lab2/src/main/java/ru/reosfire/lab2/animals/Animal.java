package ru.reosfire.lab2.animals;

import ru.reosfire.lab2.enclosures.Enclosure;

import java.time.Duration;

public abstract class Animal<TSelf extends Animal<TSelf>> {
    public final double Weight;
    public final Duration LifeTime;

    public Animal(double weight, Duration lifeTime) {
        Weight = weight;
        LifeTime = lifeTime;
    }

    public abstract void Move(Enclosure<TSelf> container);

    @Override
    public String toString() {
        return getClass().getSimpleName() + "   Weight: " + Weight + "  Lifetime: " + LifeTime.toDays() + " days";
    }
}
