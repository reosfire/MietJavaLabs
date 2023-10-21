package ru.reosfire.lab3.controller.commands.system;

import ru.reosfire.lab3.controller.CommandContext;
import ru.reosfire.lab3.controller.commands.Command;

public class CloseCommand implements Command {
    @Override
    public String getId() {
        return "exit";
    }

    @Override
    public String getName() {
        return "Exit program";
    }

    @Override
    public void execute(CommandContext context) {
        context.interruptCommandsHandling();
    }
}
