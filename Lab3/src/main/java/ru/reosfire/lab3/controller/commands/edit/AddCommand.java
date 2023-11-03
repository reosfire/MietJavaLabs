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

        switch (enclosureType) {
            case AQUARIUM: {
                double weight = context.view.requestWeight();
                Duration lifetime = context.view.requestLifetime();

                context.getZoo().createWaterfowl(weight, lifetime);
                break;
            }
            case COVERED: {
                double weight = context.view.requestWeight();
                Duration lifetime = context.view.requestLifetime();

                context.getZoo().createFeathered(weight, lifetime);
                break;
            }
            case OPENED: {
                double weight = context.view.requestWeight();
                Duration lifetime = context.view.requestLifetime();

                context.getZoo().createUngulate(weight, lifetime);
                break;
            }
            case TERRARIUM: {
                double weight = context.view.requestWeight();
                Duration lifetime = context.view.requestLifetime();

                context.getZoo().createColdBlooded(weight, lifetime);
                break;
            }
        }

        context.view.showAddSuccess();
    }
}
