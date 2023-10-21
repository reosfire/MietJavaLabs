package ru.reosfire.lab3.controller;

import ru.reosfire.lab3.configuration.Config;
import ru.reosfire.lab3.configuration.ConfigBasedOnProperties;
import ru.reosfire.lab3.controller.commands.Command;
import ru.reosfire.lab3.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {
    private final CommandContext context;
    private final Map<String, Command> commands;

    public Controller(List<Command> commands) {
        this.commands = createCommandsMap(commands);
        this.context = constructContext();
    }

    public void startLooping() {
        while (!context.isInterrupted()) {
            Command requestedCommand = commands.get(context.view.requestCommandId());
        }
    }

    private Map<String, Command> createCommandsMap(List<Command> commands) {
        Map<String, Command> result = new HashMap<>();

        for (Command command : commands) {
            result.put(command.getId(), command);
        }

        return result;
    }

    private CommandContext constructContext() {
        Config config = new ConfigBasedOnProperties("Program");
        View view = new View();
        return new CommandContext(config, view);
    }
}
