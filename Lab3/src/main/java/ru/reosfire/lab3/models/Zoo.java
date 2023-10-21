package ru.reosfire.lab3.models;

import ru.reosfire.lab3.models.animals.ColdBlooded;
import ru.reosfire.lab3.models.animals.Feathered;
import ru.reosfire.lab3.models.animals.Ungulate;
import ru.reosfire.lab3.models.animals.Waterfowl;
import ru.reosfire.lab3.models.enclosures.Aquarium;
import ru.reosfire.lab3.models.enclosures.Covered;
import ru.reosfire.lab3.models.enclosures.Opened;
import ru.reosfire.lab3.models.enclosures.Terrarium;

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
}
