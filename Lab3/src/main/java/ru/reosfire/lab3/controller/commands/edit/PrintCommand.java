package ru.reosfire.lab3.controller.commands.edit;

import ru.reosfire.lab3.controller.CommandContext;
import ru.reosfire.lab3.controller.commands.Command;
import ru.reosfire.lab3.models.Zoo;

public class PrintCommand implements Command {
    @Override
    public String getId() {
        return "print";
    }

    @Override
    public String getName() {
        return "Print all data about current zoo";
    }

    @Override
    public void execute(CommandContext context) {
        Zoo zoo = context.getZoo();
        context.view.printZoo(zoo);
    }
}
