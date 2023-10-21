package ru.reosfire.lab3.controller;

import ru.reosfire.lab3.configuration.Config;
import ru.reosfire.lab3.view.View;

public class CommandContext {

    public final Config config;
    public final View view;

    private boolean interrupted = false;

    public CommandContext(Config config, View view) {
        this.config = config;
        this.view = view;
    }

    public boolean isInterrupted() {
        return interrupted;
    }

    public void interruptCommandsHandling() {
        view.close();
        interrupted = true;
    }
}
