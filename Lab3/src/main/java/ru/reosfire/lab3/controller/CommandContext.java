package ru.reosfire.lab3.controller;

import ru.reosfire.lab3.configuration.Config;
import ru.reosfire.lab3.models.Zoo;
import ru.reosfire.lab3.view.View;

import java.io.File;

public class CommandContext {

    public final File DATABASE_FILE = new File("ZooDB.txt");

    public final Config config;
    public final View view;
    public final boolean debugEnabled;

    private Zoo zoo;
    private boolean interrupted = false;

    public CommandContext(Config config, View view, Zoo zoo, boolean debugEnabled) {
        this.config = config;
        this.view = view;
        this.zoo = zoo;
        this.debugEnabled = debugEnabled;
    }

    public boolean isInterrupted() {
        return interrupted;
    }

    public void interruptCommandsHandling() {
        view.close();
        interrupted = true;
    }

    public Zoo getZoo() {
        return zoo;
    }
    public void setZoo(Zoo zoo) {
        this.zoo = zoo;
    }
}
