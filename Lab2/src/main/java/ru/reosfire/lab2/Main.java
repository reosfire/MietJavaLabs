package ru.reosfire.lab2;

import ru.reosfire.lab2.animals.ColdBlooded;
import ru.reosfire.lab2.animals.Feathered;
import ru.reosfire.lab2.animals.Ungulate;
import ru.reosfire.lab2.animals.Waterfowl;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        Zoo zoo = new Zoo();
        ColdBlooded coldBlooded = zoo.createColdBlooded(10, Duration.ofDays(12));
        Feathered feathered = zoo.createFeathered(1, Duration.ofDays(12));
        Feathered feathered1 = zoo.createFeathered(2, Duration.ofDays(32));
        Ungulate ungulate = zoo.createUngulate(2000, Duration.ofDays(12));
        Waterfowl waterfowl = zoo.createWaterfowl(300, Duration.ofDays(356));

        coldBlooded.Move();
        feathered.Move();
        feathered1.Move();
        ungulate.Move();
        waterfowl.Move();

        zoo.print();
    }
}