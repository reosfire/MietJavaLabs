package ru.reosfire.lab2;

import ru.reosfire.lab2.animals.ColdBlooded;
import ru.reosfire.lab2.animals.Feathered;
import ru.reosfire.lab2.animals.Ungulates;
import ru.reosfire.lab2.animals.Waterfowl;
import ru.reosfire.lab2.enclosures.Aquarium;
import ru.reosfire.lab2.enclosures.Covered;
import ru.reosfire.lab2.enclosures.Opened;
import ru.reosfire.lab2.enclosures.WithInfraredLight;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        ColdBlooded coldBlooded = new ColdBlooded(10, Duration.ofDays(12));
        Feathered feathered1 = new Feathered(1, Duration.ofDays(12));
        Feathered feathered2 = new Feathered(2, Duration.ofDays(32));
        Ungulates ungulates = new Ungulates(2000, Duration.ofDays(12));
        Waterfowl waterfowl = new Waterfowl(300, Duration.ofDays(356));

        WithInfraredLight withInfraredLight = new WithInfraredLight();
        Aquarium aquarium = new Aquarium();
        Covered covered = new Covered();
        Opened opened = new Opened();


        coldBlooded.Move(withInfraredLight);
        //coldBlooded.Move(aquarium);

        feathered1.Move(covered);
        feathered2.Move(covered);

        ungulates.Move(opened);

        waterfowl.Move(aquarium);


        System.out.println(withInfraredLight);
        System.out.println(aquarium);
        System.out.println(covered);
        System.out.println(opened);
    }
}