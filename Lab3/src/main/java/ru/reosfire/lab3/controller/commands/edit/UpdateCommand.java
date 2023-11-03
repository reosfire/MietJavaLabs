package ru.reosfire.lab3.controller.commands.edit;

import ru.reosfire.lab3.controller.CommandContext;
import ru.reosfire.lab3.controller.commands.Command;
import ru.reosfire.lab3.view.PropertyType;

import java.time.Duration;

public class UpdateCommand implements Command {
    @Override
    public String getId() {
        return "update";
    }

    @Override
    public String getName() {
        return "Update animal by id";
    }

    @Override
    public void execute(CommandContext context) {
        int id = context.view.requestAnimalIdToUpdate();
        PropertyType propertyType = context.view.requestPropertyType();

        try {
            switch (propertyType) {
                case WEIGHT: {
                    double weight = context.view.requestWeight();
                    context.getZoo().updateWeight(id, weight);
                    break;
                }
                case LIFETIME: {
                    Duration lifetime = context.view.requestLifetime();
                    context.getZoo().updateLifetime(id, lifetime);
                    break;
                }
            }
            context.view.showUpdateSuccess(id);
        } catch (Exception e) {
            context.view.showUpdateIdNotFoundError(id);
        }
    }
}
