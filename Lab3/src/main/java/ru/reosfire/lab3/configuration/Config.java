package ru.reosfire.lab3.configuration;

public interface Config {
    String getLogin();
    String getPassword();
    UserGroup getGroup();
    Boolean isDebugMode();
    Boolean isAutotestsMode();
}
