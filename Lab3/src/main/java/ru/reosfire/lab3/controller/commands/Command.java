package ru.reosfire.lab3.controller.commands;

import ru.reosfire.lab3.controller.CommandContext;

public interface Command {

    String getId();
    String getName();
    void execute(CommandContext context);
}
