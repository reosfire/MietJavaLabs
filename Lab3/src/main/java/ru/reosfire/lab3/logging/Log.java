package ru.reosfire.lab3.logging;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    private static Logger logger = null;
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static int logLevel = 0; // 0 nothing, 1 critical errors, 2 errors, 3 warnings, 4 info

    public static void init(String path, int logLevel) {
        disposeCurrent();

        Log.logLevel = logLevel;

        try {
            logger = new Logger(path);
            i("");
            i("New Logger initialized");
            i("");
        } catch (Exception e) {
            throw new RuntimeException("Error while creating new logger", e);
        }
    }

    public static void disposeCurrent() {
        if (logger == null) return;
        logger.close();
    }

    public static void i(Object info) {
        i(info.toString());
    }
    public static void i(String info) {
        assertLoggerInitialized();
        printDateTime();
        logger.print("info");
        logger.print("  | ");
        logger.println(info);
    }

    public static void e(Object error) {
        e(error.toString());
    }
    public static void e(String error) {
        assertLoggerInitialized();
        printDateTime();
        logger.print("error");
        logger.print(" | ");
        logger.println(error);
    }

    private static void printDateTime() {
        String dateTimeString = LocalDateTime.now().format(DATE_TIME_FORMAT);
        logger.print(dateTimeString);
        logger.print(" | ");
    }

    private static void assertLoggerInitialized() {
        if (logger == null) throw new IllegalStateException("Logger does not initialized, please call Log::init before");
    }
}
