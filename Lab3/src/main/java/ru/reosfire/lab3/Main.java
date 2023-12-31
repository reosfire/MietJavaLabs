package ru.reosfire.lab3;

import ru.reosfire.lab3.authentication.AuthorityVerifier;
import ru.reosfire.lab3.authentication.AuthorityVerifierByConfig;
import ru.reosfire.lab3.configuration.Config;
import ru.reosfire.lab3.configuration.ConfigBasedOnProperties;
import ru.reosfire.lab3.controller.Controller;
import ru.reosfire.lab3.controller.ControllerFactory;
import ru.reosfire.lab3.logging.Log;

public class Main {
    public static void main(String[] args) {
        Config config = new ConfigBasedOnProperties("custom.props");

        Log.init("./Log.txt", config.logLevel());

        try {
            AuthorityVerifier authorityVerifier = new AuthorityVerifierByConfig(config);

            ControllerFactory controllerFactory = new ControllerFactory(config, authorityVerifier);
            Controller controller = controllerFactory.create();
            controller.startLooping();
        } catch (Exception e) {
            Log.ce(e.getMessage());
        }
    }
}