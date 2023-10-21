package ru.reosfire.lab3.view;

import ru.reosfire.lab3.authentication.Credentials;

public class View implements AutoCloseable {
    private final ConsolePresenter presenter = new ConsolePresenter();

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
