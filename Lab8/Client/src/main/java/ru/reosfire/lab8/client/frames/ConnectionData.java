package ru.reosfire.lab8.client.frames;

public class ConnectionData {
    public final String host;
    public final String login;

    public ConnectionData(String host, String login) {
        this.host = host;
        this.login = login;
    }

    public String getIP() {
        return host.split(":")[0];
    }
    public int getPort() {
        return Integer.parseInt(host.split(":")[1]);
    }
}
