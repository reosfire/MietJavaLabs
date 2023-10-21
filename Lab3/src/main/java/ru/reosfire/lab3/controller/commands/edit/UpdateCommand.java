package ru.reosfire.lab3.controller.commands.edit;

import ru.reosfire.lab3.controller.CommandContext;
import ru.reosfire.lab3.controller.commands.Command;

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

    }
}
