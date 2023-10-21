package ru.reosfire.lab3.view;

import ru.reosfire.lab3.authentication.Credentials;
import ru.reosfire.lab3.controller.commands.Command;

import java.util.List;

public class View implements AutoCloseable {
    private static final int commandsListLength = 60;

    private final ConsolePresenter presenter = new ConsolePresenter();

    public void printCommandsList(List<Command> commands) {
        presenter.printLine();
        for (Command command : commands) {
            String name = command.getName();
            String id = command.getId();
            int sumLen = name.length() + id.length();
            presenter.printInfoLine(name + " " + presenter.repeatingString('.', commandsListLength - sumLen - 2) + " " + id);
        }
    }

    public Credentials readCredentials() {
        presenter.printWelcomeLine("Authorization:");
        String login = presenter.readNotEmptyString("Login: ", 1);
        String password = presenter.readNotEmptyString("Password: ", 1);
        return new Credentials(login, password);
    }

    public String requestCommandId() {
        return presenter.readLine("Enter command from list above: ");
    }

    public void showUnknownCommandError() {
        presenter.printError("You entered incorrect command. Please use command from list above");
    }
    public void showUnauthorizedError() {
        presenter.printError("Wrong login-password combination.");
    }

    @Override
    public void close() {
        try {
            presenter.close();
        } catch (Exception e) {
            throw new RuntimeException("Error while closing view", e);
        }
    }
}
