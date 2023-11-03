package ru.reosfire.lab3.configuration;

import ru.reosfire.lab3.authentication.User;
import ru.reosfire.lab3.authentication.UserGroup;

public interface Config {
    User getUser();
    Boolean isDebugMode();
    Boolean isAutotestsMode();

    default int logLevel() {
        if (isDebugMode() || getUser().getGroup() == UserGroup.ROOT)
            return 4;
        else
            return 2;
    }
}
