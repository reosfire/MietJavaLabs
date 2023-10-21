package ru.reosfire.lab3.controller.commands.edit;

import ru.reosfire.lab3.controller.CommandContext;
import ru.reosfire.lab3.controller.commands.Command;

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

    }
}
