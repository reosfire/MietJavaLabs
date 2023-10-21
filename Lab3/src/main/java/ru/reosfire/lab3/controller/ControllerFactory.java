package ru.reosfire.lab3.controller;

import ru.reosfire.lab3.controller.commands.CloseCommand;
import ru.reosfire.lab3.controller.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class ControllerFactory {
    public Controller createForUser() {
        List<Command> commands = new ArrayList<>();
        commands.add(new CloseCommand());
        return new Controller(commands);
    }

    public Controller createForRoot() {
        List<Command> commands = new ArrayList<>();
        commands.add(new CloseCommand());
        return new Controller(commands);
    }
}
