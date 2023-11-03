package ru.reosfire.lab3.controller;

import ru.reosfire.lab3.controller.commands.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {
    private final CommandContext context;
    private final Map<String, Command> commandsMap;
    private final List<Command> commandsList;

    public Controller(List<Command> commands, CommandContext context) {
        this.commandsList = commands;
        this.commandsMap = createCommandsMap(commands);

        this.context = context;
    }

    public void startLooping() {
        context.view.printProgramWelcomeFor(context.config.getUser());

        while (!context.isInterrupted()) {
            context.view.printCommandsList(commandsList);
            Command requestedCommand = commandsMap.get(context.view.requestCommandId());

            if (requestedCommand == null) {
                context.view.showUnknownCommandError();
                continue;
            }
            requestedCommand.execute(context);
        }
    }

    private Map<String, Command> createCommandsMap(List<Command> commands) {
        Map<String, Command> result = new HashMap<>();

        for (Command command : commands) {
            result.put(command.getId(), command);
        }

        return result;
    }
}
