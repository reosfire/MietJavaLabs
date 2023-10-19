package ru.reosfire.lab3.configuration;

import ru.reosfire.lab3.authentication.User;

public interface Config {
    User getUser();
    Boolean isDebugMode();
    Boolean isAutotestsMode();
}
