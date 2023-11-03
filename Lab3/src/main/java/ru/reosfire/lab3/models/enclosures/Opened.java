package ru.reosfire.lab3.models.enclosures;

import ru.reosfire.lab3.models.animals.Ungulate;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Opened extends Enclosure<Ungulate> {

    @Override
    public void serialize(OutputStreamWriter writer) throws IOException {
        writer.append(String.valueOf(animals.size())).append("\n");

        for (Ungulate animal : animals) {
            animal.serialize(writer);
        }
    }

    public static Opened deserialize(Scanner scanner) {
        int n = Integer.parseInt(scanner.nextLine());

        Opened result = new Opened();

        for (int i = 0; i < n; i++) {
            result.add(Ungulate.deserialize(scanner, result));
        }

        return result;
    }
}
