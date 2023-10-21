package ru.reosfire.lab3.authentication;

import ru.reosfire.lab3.configuration.Config;

public class AuthorityVerifierByConfig implements AuthorityVerifier {

    private final String expectedLogin;
    private final String expectedPassword;

    public AuthorityVerifierByConfig(Config config) {
        expectedLogin = config.getUser().getLogin();
        expectedPassword = config.getUser().getPassword();
    }

    @Override
    public boolean authorize(Credentials credentials) {
        return credentials.login.equals(expectedLogin) && credentials.password.equals(expectedPassword);
    }
}
