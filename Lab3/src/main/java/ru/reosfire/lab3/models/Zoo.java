package ru.reosfire.lab3.models;

import ru.reosfire.lab3.models.animals.ColdBlooded;
import ru.reosfire.lab3.models.animals.Feathered;
import ru.reosfire.lab3.models.animals.Ungulate;
import ru.reosfire.lab3.models.animals.Waterfowl;
import ru.reosfire.lab3.models.enclosures.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.time.Duration;
import java.util.Scanner;

public class Zoo {
    public final Terrarium terrarium;
    public final Aquarium aquarium;
    public final Covered covered;
    public final Opened opened;

    public Zoo() {
        terrarium = new Terrarium();
        aquarium = new Aquarium();
        covered = new Covered();
        opened = new Opened();
    }

    public Zoo(Terrarium terrarium, Aquarium aquarium, Covered covered, Opened opened) {
        this.terrarium = terrarium;
        this.aquarium = aquarium;
        this.covered = covered;
        this.opened = opened;
    }

    public ColdBlooded createColdBlooded(double weight, Duration lifeTime) {
        ColdBlooded coldBlooded = new ColdBlooded(weight, lifeTime, terrarium);
        terrarium.add(coldBlooded);
        return coldBlooded;
    }
    public Waterfowl createWaterfowl(double weight, Duration lifeTime) {
        Waterfowl waterfowl = new Waterfowl(weight, lifeTime, aquarium);
        aquarium.add(waterfowl);
        return waterfowl;
    }
    public Feathered createFeathered(double weight, Duration lifeTime) {
        Feathered feathered = new Feathered(weight, lifeTime, covered);
        covered.add(feathered);
        return feathered;
    }
    public Ungulate createUngulate(double weight, Duration lifeTime) {
        Ungulate ungulate = new Ungulate(weight, lifeTime, opened);
        opened.add(ungulate);
        return ungulate;
    }

    public void removeById(int id) {
        for (Enclosure<?> enclosure : getEnclosures()) {
            try {
                enclosure.removeById(id);
                return;
            } catch (Exception ignored) {

            }
        }
        throw new RuntimeException("Animal with id: " + id + " not found");
    }

    public void serializeToFile(File file) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream)
        ) {
            for (Enclosure<?> enclosure : getEnclosures()) {
                enclosure.serialize(writer);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while serializing Zoo", e);
        }
    }

    public static Zoo deserializeFromFile(File file) {
        try (Scanner scanner = new Scanner(file)) {
            Terrarium terrarium = Terrarium.deserialize(scanner);
            Aquarium aquarium = Aquarium.deserialize(scanner);
            Covered covered = Covered.deserialize(scanner);
            Opened opened = Opened.deserialize(scanner);

            return new Zoo(terrarium, aquarium, covered, opened);
        } catch (Exception e) {
            throw new RuntimeException("Error while deserializing Zoo", e);
        }
    }

    private Enclosure<?>[] getEnclosures() {
        return new Enclosure<?>[] {
                terrarium,
                aquarium,
                covered,
                opened,
        };
    }
}
