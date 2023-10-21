package ru.reosfire.lab3.controller.commands.edit;

import ru.reosfire.lab3.controller.CommandContext;
import ru.reosfire.lab3.controller.commands.Command;

public class RemoveCommand implements Command {
    @Override
    public String getId() {
        return "remove";
    }

    @Override
    public String getName() {
        return "Remove animal from an enclosure";
    }

    @Override
    public void execute(CommandContext context) {

    }
}