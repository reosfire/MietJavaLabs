package ru.reosfire.lab3.models.animals;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.Duration;

public abstract class Animal {
    private static int createdCount = 0;

    public final double Weight;
    public final Duration LifeTime;
    public final int Id;

    public Animal(double weight, Duration lifeTime) {
        Weight = weight;
        LifeTime = lifeTime;
        Id = ++createdCount;
    }

    public abstract void Move();

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" +
                "\n   Weight: " + Weight +
                "\n   Lifetime: " + LifeTime.toDays() + " days" +
                "\n   Id: " + Id + "\n";
    }

    public abstract void serialize(OutputStreamWriter writer) throws IOException;
}
