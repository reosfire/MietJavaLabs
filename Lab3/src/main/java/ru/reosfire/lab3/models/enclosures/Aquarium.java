package ru.reosfire.lab3.models.enclosures;

import ru.reosfire.lab3.models.animals.Waterfowl;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Aquarium extends Enclosure<Waterfowl> {

    @Override
    public void serialize(OutputStreamWriter writer) throws IOException {
        writer.append(String.valueOf(animals.size())).append("\n");

        for (Waterfowl animal : animals) {
            animal.serialize(writer);
        }
    }

    public static Aquarium deserialize(Scanner scanner) {
        int n = Integer.parseInt(scanner.nextLine());

        Aquarium result = new Aquarium();

        for (int i = 0; i < n; i++) {
            result.add(Waterfowl.deserialize(scanner, result));
        }

        return result;
    }
}
