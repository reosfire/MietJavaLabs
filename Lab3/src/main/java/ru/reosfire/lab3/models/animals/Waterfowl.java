package ru.reosfire.lab3.models.animals;

import ru.reosfire.lab3.models.enclosures.Aquarium;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.Duration;
import java.util.Scanner;

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

    @Override
    public void serialize(OutputStreamWriter writer) throws IOException {
        writer.append(String.valueOf(Weight)).append("\n");
        writer.append(String.valueOf(LifeTime)).append("\n");
    }

    public static Waterfowl deserialize(Scanner scanner, Aquarium container) {
        double weight = Double.parseDouble(scanner.nextLine());
        Duration lifetime = Duration.parse(scanner.nextLine());

        return new Waterfowl(weight, lifetime, container);
    }
}
