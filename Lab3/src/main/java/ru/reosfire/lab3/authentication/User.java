package ru.reosfire.lab3.authentication;

public class User {
    private final String login;
    private final String password;
    private final UserGroup group;


    public User(String login, String password, UserGroup group) {
        this.login = login;
        this.password = password;
        this.group = group;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UserGroup getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return "Login: " +  login + "   Password: " + password + "   Group: " + group.name();
    }
}
