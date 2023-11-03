package ru.reosfire.lab3.models.animals;

import ru.reosfire.lab3.models.enclosures.Opened;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.Duration;
import java.util.Scanner;

public class Ungulate extends Animal {
    private final Opened container;
    public Ungulate(double weight, Duration lifeTime, Opened container) {
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

    public static Ungulate deserialize(Scanner scanner, Opened container) {
        double weight = Double.parseDouble(scanner.nextLine());
        Duration lifetime = Duration.parse(scanner.nextLine());

        return new Ungulate(weight, lifetime, container);
    }
}
