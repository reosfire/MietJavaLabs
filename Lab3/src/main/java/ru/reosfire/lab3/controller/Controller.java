package ru.reosfire.lab3.controller;

import ru.reosfire.lab3.authentication.User;
import ru.reosfire.lab3.controller.commands.Command;
import ru.reosfire.lab3.controller.commands.system.AutotestsCommand;
import ru.reosfire.lab3.logging.Log;

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
        beforeLoopInitiated();

        while (!context.isInterrupted()) {
            context.view.showCommandsList(commandsList);
            Log.i("request command from user");
            Command requestedCommand = commandsMap.get(context.view.requestCommandId());

            if (requestedCommand == null) {
                context.view.showUnknownCommandError();
                Log.i("user entered unknown command");
                continue;
            }
            try {
                requestedCommand.execute(context);
                Log.i("command: " + requestedCommand.getId() + " successfully executed");
            } catch (Exception e) {
                Log.ce(e.getMessage());
                context.view.showUnexpectedError(requestedCommand.getId());
            }
        }
    }

    private void beforeLoopInitiated() {
        User user = context.config.getUser();
        context.view.showProgramWelcomeFor(user);
        Log.i("User: " + user.getLogin() + " logged in");

        if (context.config.isDebugMode()) {
            context.view.showAutotestsHeader();
            new AutotestsCommand().execute(context);
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
