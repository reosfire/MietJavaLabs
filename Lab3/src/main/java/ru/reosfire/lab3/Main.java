package ru.reosfire.lab3;

import ru.reosfire.lab3.configuration.Config;
import ru.reosfire.lab3.configuration.ConfigBasedOnProperties;
import ru.reosfire.lab3.logging.Log;

public class Main {
    public static void main(String[] args) {
        Log.init("./Log.txt");
        Log.i("very important info");

        Config config = new ConfigBasedOnProperties("custom.props");
        Log.i(config.getLogin());
        Log.i(config.getPassword());
        Log.i(config.getGroup().name());
        Log.i(config.isDebugMode().toString());
        Log.i(config.isAutotestsMode().toString());
    }
}