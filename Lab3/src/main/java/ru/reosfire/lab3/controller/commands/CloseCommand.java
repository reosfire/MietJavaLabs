package ru.reosfire.lab3.controller.commands;

import ru.reosfire.lab3.controller.CommandContext;

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
