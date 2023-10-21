package ru.reosfire.lab3.view;

import java.util.Scanner;

public class View implements AutoCloseable {

    private final Scanner input = new Scanner(System.in);

    public void showUnknownCommandError() {
        printError("Вы ввели некоррекнтую команду. Можно использовать только команды из списка!");
    }

    public String requestCommandId() {
        System.out.print("Введите команду из списка выше: ");
        return input.nextLine();
    }

    private void printError(String error) {
        System.out.println(ConsoleColors.RED + error + ConsoleColors.RESET);
    }

    @Override
    public void close() {
        try {
            input.close();
        } catch (Exception e) {
            throw new RuntimeException("Error while closing view", e);
        }
    }
}
