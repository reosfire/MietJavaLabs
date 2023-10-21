package ru.reosfire.lab2;

import ru.reosfire.lab2.animals.ColdBlooded;
import ru.reosfire.lab2.animals.Feathered;
import ru.reosfire.lab2.animals.Ungulate;
import ru.reosfire.lab2.animals.Waterfowl;
import ru.reosfire.lab2.enclosures.Aquarium;
import ru.reosfire.lab2.enclosures.Covered;
import ru.reosfire.lab2.enclosures.Opened;
import ru.reosfire.lab2.enclosures.Terrarium;

import java.time.Duration;

public class Zoo {
    private final Terrarium terrarium = new Terrarium();
    private final Aquarium aquarium = new Aquarium();
    private final Covered covered = new Covered();
    private final Opened opened = new Opened();

    public ColdBlooded createColdBlooded(double weight, Duration lifeTime) {
        return new ColdBlooded(weight, lifeTime, terrarium);
    }
    public Waterfowl createWaterfowl(double weight, Duration lifeTime) {
        return new Waterfowl(weight, lifeTime, aquarium);
    }
    public Feathered createFeathered(double weight, Duration lifeTime) {
        return new Feathered(weight, lifeTime, covered);
    }
    public Ungulate createUngulate(double weight, Duration lifeTime) {
        return new Ungulate(weight, lifeTime, opened);
    }

    public void print() {
        System.out.println("Terrarium: ");
        System.out.println(terrarium);

        System.out.println("Aquarium: ");
        System.out.println(aquarium);

        System.out.println("Opened: ");
        System.out.println(opened);

        System.out.println("Covered: ");
        System.out.println(covered);
    }
}
