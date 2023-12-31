package ru.reosfire.lab3.view;

import ru.reosfire.lab3.authentication.Credentials;
import ru.reosfire.lab3.authentication.User;
import ru.reosfire.lab3.controller.commands.Command;
import ru.reosfire.lab3.models.Zoo;

import java.time.Duration;
import java.util.List;

public class View implements AutoCloseable {
    private static final int commandsListLength = 60;

    private final ConsolePresenter presenter = new ConsolePresenter();

    public void showZoo(Zoo zoo) {
        presenter.printHeader(" ZOO ");

        presenter.printLine("Terrarium:");
        presenter.printBlock(zoo.terrarium.toString(), 1);

        presenter.printLine("Aquarium:");
        presenter.printBlock(zoo.aquarium.toString(), 1);

        presenter.printLine("Covered:");
        presenter.printBlock(zoo.covered.toString(), 1);

        presenter.printLine("Opened:");
        presenter.printBlock(zoo.opened.toString(), 1);
    }

    public void showCommandsList(List<Command> commands) {
        presenter.printLine();
        for (Command command : commands) {
            String name = command.getName();
            String id = command.getId();
            int sumLen = name.length() + id.length();
            String dots = ConsolePresenter.repeatingString('.', commandsListLength - sumLen - 2);
            presenter.printInfoLine(name + " " + dots + " " + id);
        }
    }

    public void showProgramWelcomeFor(User user) {
        presenter.printLine();
        presenter.printSuccess("Welcome " + user.getLogin() + "! Your group is: " + user.getGroup().name());
    }

    public void showAutotestsHeader() {
        presenter.printHeader("AUTOTESTS");
    }

    public Credentials requestCredentials() {
        presenter.printWelcomeLine("Authorization:");
        String login = presenter.readNotEmptyString("Login: ", 1);
        String password = presenter.readNotEmptyString("Password: ", 1);
        return new Credentials(login, password);
    }

    public double requestWeight() {
        return presenter.readDouble("Enter animal weight: ");
    }

    public Duration requestLifetime() {
        return Duration.ofDays(presenter.readInt("Enter animal lifetime days: "));
    }

    public int requestAnimalIdToRemove() {
        return presenter.readInt("Enter id of animal to be removed: ");
    }
    public int requestAnimalIdToUpdate() {
        return presenter.readInt("Enter id of animal to be updated: ");
    }

    public String requestCommandId() {
        return presenter.readNotEmptyString("Enter command from list above: ");
    }

    public EnclosureType requestEnclosureTypeToAddAnimal() {
        return presenter.readEnclosureType("Enter enclosure type in which you wish to add animal");
    }
    public PropertyType requestPropertyType() {
        return presenter.readPropertyType("Enter property that you want to change");
    }

    public void showUnknownCommandError() {
        presenter.printError("You entered incorrect command. Please use command from list above");
    }
    public void showUnauthorizedError() {
        presenter.printError("Wrong login-password combination.");
    }
    public void showDeserializationError() {
        presenter.printError("Error while deserializing zoo. Watch logs for more details");
    }
    public void showSerializationError() {
        presenter.printError("Error while serializing zoo. Watch logs for more details");
    }
    public void showSerializationSuccess() {
        presenter.printSuccess("Serialization end-up successfully");
    }
    public void showDeserializationSuccess() {
        presenter.printSuccess("Deserialization end-up successfully");
    }
    public void showAddSuccess() {
        presenter.printSuccess("New animal successfully added to zoo");
    }
    public void showRemoveSuccess(int id) {
        presenter.printSuccess("Animal with id: " + id + " successfully removed from zoo");
    }
    public void showRemoveIdNotFoundError(int id) {
        presenter.printError("Animal with id: " + id + " not found");
    }
    public void showUnexpectedError(String command) {
        presenter.printError("Unexpected error occurred while processing command: " + command);
    }
    public void showUpdateSuccess(int id) {
        presenter.printSuccess("Animal with id: " + id + " successfully updated");
    }
    public void showUpdateIdNotFoundError(int id) {
        presenter.printError("Animal with id: " + id + " not found");
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
