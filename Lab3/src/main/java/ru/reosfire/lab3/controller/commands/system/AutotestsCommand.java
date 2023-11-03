package ru.reosfire.lab3.controller.commands.system;

import ru.reosfire.lab3.controller.CommandContext;
import ru.reosfire.lab3.controller.commands.Command;

public class AutotestsCommand implements Command {
    @Override
    public String getId() {
        return "autotest";
    }

    @Override
    public String getName() {
        return "Run autotests";
    }

    @Override
    public void execute(CommandContext context) {
        //TODO write to log results
    }
}
