package ru.reosfire.lab5.data;

import ru.reosfire.lab5.foudation.Graph;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileGraphReader {
    public static Graph readFrom(String fileName, Color color) throws IOException {
        File file = new File(fileName);

        InputStream inputStream = Files.newInputStream(file.toPath());
        Scanner scanner = new Scanner(inputStream);

        List<Double> xsList = new ArrayList<>();
        List<Double> ysList = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String[] tokens = scanner.nextLine().split(" ");
            xsList.add(Double.parseDouble(tokens[0]));
            ysList.add(Double.parseDouble(tokens[1]));
        }

        double[] xs = new double[xsList.size()];
        double[] ys = new double[ysList.size()];

        for (int i = 0; i < xsList.size(); i++) {
            xs[i] = xsList.get(i);
            ys[i] = ysList.get(i);
        }

        Graph result = new Graph(xs, ys, color, file.getName().split("\\.")[0]);
        return result;
    }

}
