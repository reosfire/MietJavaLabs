package ru.reosfire.lab3.controller;

import ru.reosfire.lab3.authentication.AuthorityVerifier;
import ru.reosfire.lab3.authentication.UnauthorizedException;
import ru.reosfire.lab3.authentication.User;
import ru.reosfire.lab3.authentication.UserGroup;
import ru.reosfire.lab3.configuration.Config;
import ru.reosfire.lab3.controller.commands.Command;
import ru.reosfire.lab3.controller.commands.edit.AddCommand;
import ru.reosfire.lab3.controller.commands.edit.PrintCommand;
import ru.reosfire.lab3.controller.commands.edit.RemoveCommand;
import ru.reosfire.lab3.controller.commands.edit.UpdateCommand;
import ru.reosfire.lab3.controller.commands.edit.serialization.ReadCommand;
import ru.reosfire.lab3.controller.commands.edit.serialization.WriteCommand;
import ru.reosfire.lab3.controller.commands.system.AutotestsCommand;
import ru.reosfire.lab3.controller.commands.system.CloseCommand;
import ru.reosfire.lab3.models.Zoo;
import ru.reosfire.lab3.view.View;

import java.util.ArrayList;
import java.util.List;

public class ControllerFactory {

    private final Config config;
    private final View view;
    private final AuthorityVerifier authorityVerifier;

    public ControllerFactory(Config config, AuthorityVerifier authorityVerifier) {
        this.config = config;
        this.authorityVerifier = authorityVerifier;
        this.view = new View();
    }

    public Controller create() {
        while (true) {
            try {
                return tryCreateOnce();
            } catch (UnauthorizedException e) {
                view.showUnauthorizedError();
                //TODO log
            }
        }
    }

    private Controller tryCreateOnce() throws UnauthorizedException {
        if (authorityVerifier.authorize(view.readCredentials()))
            return createFor(config.getUser());
        else throw new UnauthorizedException();
    }

    private Controller createFor(User user) {
        if (user.getGroup() == UserGroup.USER)
            return createForUser();
        else
            return createForRoot();
    }

    private Controller createForUser() {
        return new Controller(commandsForUser(), createContext());
    }

    private Controller createForRoot() {
        return new Controller(commandsForRoot(), createContext());
    }

    private List<Command> commandsForUser() {
        List<Command> commands = new ArrayList<>();
        commands.add(new CloseCommand());

        commands.add(new ReadCommand());
        commands.add(new WriteCommand());

        commands.add(new PrintCommand());

        commands.add(new AddCommand());
        commands.add(new RemoveCommand());
        commands.add(new UpdateCommand());
        return commands;
    }

    private List<Command> commandsForRoot() {
        List<Command> commands = commandsForUser();
        commands.add(new AutotestsCommand());
        return commands;
    }

    private CommandContext createContext() {
        boolean debugMode = config.getUser().getGroup() == UserGroup.ROOT || config.isDebugMode();
        return new CommandContext(config, view, new Zoo(), debugMode);
    }
}
