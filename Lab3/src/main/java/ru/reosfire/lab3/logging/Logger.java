package ru.reosfire.lab3.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger implements AutoCloseable {
    private final FileWriter writer;
    private boolean closed = false;

    public Logger(String path) throws IOException {
        writer = new FileWriter(path, true);
    }
    public Logger(File file) throws IOException {
        writer = new FileWriter(file, true);
    }

    public void println(String line) {
        print(line);
        print("\n");
    }
    public void println(Object object) {
        print(object);
        print("\n");
    }

    public void print(Object object) {
        print(object.toString());
    }
    public void print(String string) {
        try {
            writer.write(string);
            writer.flush();
        } catch (Exception e) {
            throw new RuntimeException("Exception while loggin wtf", e);
        }
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    public void close() {
        if (closed) return;
        try {
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error while closing log file", e);
        }
        closed = true;
    }
}
