package ru.reosfire.lab3.controller.commands.edit;

import ru.reosfire.lab3.controller.CommandContext;
import ru.reosfire.lab3.controller.commands.Command;
import ru.reosfire.lab3.view.EnclosureType;

import java.time.Duration;

public class AddCommand implements Command {
    @Override
    public String getId() {
        return "add";
    }

    @Override
    public String getName() {
        return "Add new animal to an enclosure";
    }

    @Override
    public void execute(CommandContext context) {
        EnclosureType enclosureType = context.view.requestEnclosureTypeToAddAnimal();
        double weight = context.view.requestWeight();
        Duration lifetime = context.view.requestLifetime();

        switch (enclosureType) {
            case AQUARIUM: {
                context.getZoo().createWaterfowl(weight, lifetime);
                break;
            }
            case COVERED: {
                context.getZoo().createFeathered(weight, lifetime);
                break;
            }
            case OPENED: {
                context.getZoo().createUngulate(weight, lifetime);
                break;
            }
            case TERRARIUM: {
                context.getZoo().createColdBlooded(weight, lifetime);
                break;
            }
        }

        context.view.showAddSuccess();
    }
}
