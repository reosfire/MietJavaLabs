package ru.reosfire.lab3.configuration;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigBasedOnProperties implements Config {
    private final static String DEFAULT_PROPERTIES_RESOURCE = "/Default.properties";

    private final String login;
    private final String password;
    private final UserGroup group;
    private final boolean debug;
    private final boolean autotests;

    public ConfigBasedOnProperties(String path) {
        Properties properties = loadProperties(path);

        login = properties.getProperty("login");
        password = properties.getProperty("password");
        group = UserGroup.valueOf(properties.getProperty("group"));
        debug = Boolean.parseBoolean(properties.getProperty("debug"));
        autotests = Boolean.parseBoolean(properties.getProperty("autotests"));
    }

    private Properties loadProperties(String path) {
        Properties defaults = getDefaults();
        saveDefaultsIfNeeded(path, defaults);

        Properties properties = new Properties(defaults);
        try (InputStream stream = Files.newInputStream(Paths.get(path))) {
            properties.load(stream);
        } catch (Exception e) {
            throw new RuntimeException("Error while loading properties", e);
        }

        return properties;
    }

    private void saveDefaultsIfNeeded(String path, Properties defaults) {
        File file = new File(path);
        if (file.isFile() && file.exists()) return;

        try (OutputStream outputStream = Files.newOutputStream(Paths.get(path));
             OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {
            defaults.store(writer, null);
        } catch (Exception e) {
            throw new RuntimeException("Error saving default config", e);
        }
    }

    private Properties getDefaults() {
        Properties result = new Properties();

        try (InputStream inputStream = getClass().getResourceAsStream(DEFAULT_PROPERTIES_RESOURCE)) {
            result.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Error loading default config", e);
        }

        return result;
    }

    @Override
    public String getLogin() {
        return login;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public UserGroup getGroup() {
        return group;
    }
    @Override
    public Boolean isDebugMode() {
        return debug;
    }
    @Override
    public Boolean isAutotestsMode() {
        return autotests;
    }
}
