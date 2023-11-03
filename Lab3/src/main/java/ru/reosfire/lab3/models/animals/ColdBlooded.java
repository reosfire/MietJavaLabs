package ru.reosfire.lab3.models.animals;

import ru.reosfire.lab3.models.enclosures.Terrarium;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.Duration;
import java.util.Scanner;

public class ColdBlooded extends Animal {
    private final Terrarium container;
    public ColdBlooded(double weight, Duration lifeTime, Terrarium container) {
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

    public static ColdBlooded deserialize(Scanner scanner, Terrarium container) {
        double weight = Double.parseDouble(scanner.nextLine());
        Duration lifetime = Duration.parse(scanner.nextLine());

        return new ColdBlooded(weight, lifetime, container);
    }
}
