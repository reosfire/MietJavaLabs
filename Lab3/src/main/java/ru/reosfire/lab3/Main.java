package ru.reosfire.lab3;

import ru.reosfire.lab3.configuration.Config;
import ru.reosfire.lab3.configuration.ConfigBasedOnProperties;
import ru.reosfire.lab3.controller.ControllerFactory;
import ru.reosfire.lab3.logging.Log;
import ru.reosfire.lab3.view.View;

public class Main {
    public static void main(String[] args) {
        Log.init("./Log.txt");
        Log.i("very important info");

        View v = new View();

        v.showUnknownCommandError();

        Config config = new ConfigBasedOnProperties("custom.props");
        Log.e(config.getUser());
        Log.i(config.isDebugMode());
        Log.i(config.isAutotestsMode());

        ControllerFactory controllerFactory = new ControllerFactory();
        controllerFactory.createForUser().startLooping();
    }
}