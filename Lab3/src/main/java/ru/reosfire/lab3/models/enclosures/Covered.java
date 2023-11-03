package ru.reosfire.lab3.models.enclosures;

import ru.reosfire.lab3.models.animals.Feathered;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Covered extends Enclosure<Feathered> {

    @Override
    public void serialize(OutputStreamWriter writer) throws IOException {
        writer.append(String.valueOf(animals.size())).append("\n");

        for (Feathered animal : animals) {
            animal.serialize(writer);
        }
    }

    public static Covered deserialize(Scanner scanner) {
        int n = Integer.parseInt(scanner.nextLine());

        Covered result = new Covered();

        for (int i = 0; i < n; i++) {
            result.add(Feathered.deserialize(scanner, result));
        }

        return result;
    }
}
