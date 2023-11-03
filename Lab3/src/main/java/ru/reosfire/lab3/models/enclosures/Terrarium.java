package ru.reosfire.lab3.models.enclosures;

import ru.reosfire.lab3.models.animals.ColdBlooded;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Terrarium extends Enclosure<ColdBlooded> {

    @Override
    public void serialize(OutputStreamWriter writer) throws IOException {
        writer.append(String.valueOf(animals.size())).append("\n");

        for (ColdBlooded animal : animals) {
            animal.serialize(writer);
        }
    }

    public static Terrarium deserialize(Scanner scanner) {
        int n = Integer.parseInt(scanner.nextLine());

        Terrarium result = new Terrarium();

        for (int i = 0; i < n; i++) {
            result.add(ColdBlooded.deserialize(scanner, result));
        }

        return result;
    }
}
