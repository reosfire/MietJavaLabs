package ru.reosfire.lab3.models.animals;

import ru.reosfire.lab3.models.enclosures.Covered;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.Duration;
import java.util.Scanner;

public class Feathered extends Animal {
    private final Covered container;
    public Feathered(double weight, Duration lifeTime, Covered container) {
        super(weight, lifeTime);
        this.container = container;
    }

    @Override
    public void Move() {
        container.add(this);
    }

    @Override
    public void serialize(OutputStreamWriter writer) throws IOException {
        writer.append(String.valueOf(Weight)).append("\n");
        writer.append(String.valueOf(LifeTime)).append("\n");
    }

    public static Feathered deserialize(Scanner scanner, Covered container) {
        double weight = Double.parseDouble(scanner.nextLine());
        Duration lifetime = Duration.parse(scanner.nextLine());

        return new Feathered(weight, lifetime, container);
    }
}
