package ru.reosfire.lab3.models.animals;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.Duration;

public abstract class Animal {
    public final double Weight;
    public final Duration LifeTime;

    public Animal(double weight, Duration lifeTime) {
        Weight = weight;
        LifeTime = lifeTime;
    }

    public abstract void Move();

    @Override
    public String toString() {
        return getClass().getSimpleName() + "   Weight: " + Weight + "  Lifetime: " + LifeTime.toDays() + " days";
    }

    public abstract void serialize(OutputStreamWriter writer) throws IOException;
}
