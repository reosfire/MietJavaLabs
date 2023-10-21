package ru.reosfire.lab3.view;

import java.util.Arrays;
import java.util.Scanner;

public class ConsolePresenter implements AutoCloseable {
    public final Scanner scanner = new Scanner(System.in);

    public double readDouble(String welcome) {
        return readDouble(welcome, 0);
    }
    public double readDouble(String welcome, int level) {
        while (true) {
            String read = readLine(welcome, level);
            try {
                return Double.parseDouble(read);
            } catch (Exception e) {
                printError(read + " is not an double precise float.", level);
            }
        }
    }

    public long readLong(String welcome) {
        return readLong(welcome, 0);
    }
    public long readLong(String welcome, int level) {
        while (true) {
            String read = readLine(welcome, level);
            try {
                return Long.parseLong(read);
            } catch (Exception e) {
                printError(read + " is not an long integer.", level);
            }
        }
    }

    public int readInt(String welcome) {
        return readInt(welcome, 0);
    }
    public int readInt(String welcome, int level) {
        while (true) {
            String read = readLine(welcome, level);
            try {
                return Integer.parseInt(read);
            } catch (Exception e) {
                printError(read + " is not an integer.", level);
            }
        }
    }

    public String readNotEmptyString(String welcome) {
        return readNotEmptyString(welcome, 0);
    }
    public String readNotEmptyString(String welcome, int level) {
        while (true) {
            String read = readLine(welcome, level);
            if (read != null && !read.trim().isEmpty())
                return read;
            printError("Please enter not empty string.", level);
        }
    }

    public String readLine(String welcome) {
        return readLine(welcome, 0);
    }
    public String readLine(String welcome, int level) {
        printWelcome(welcome, level);
        return scanner.nextLine();
    }


    public void printWelcome(String welcome) {
        printWelcome(welcome, 0);
    }
    public void printWelcome(String welcome, int level) {
        printYellow(getPadding(level) + welcome);
    }
    public void printWelcomeLine(String welcome) {
        printWelcomeLine(welcome, 0);
    }
    public void printWelcomeLine(String welcome, int level) {
        printYellowLine(getPadding(level) + welcome);
    }

    public void printError(String error) {
        printError(error, 0);
    }
    public void printError(String error, int level) {
        printRedLine(getPadding(level) + error);
    }

    public void printHeader(String header) {
        printHeader(header, 100);
    }
    public void printHeader(String header, int length) {
        int headerLen = header.length();
        int startLen = (length - headerLen) / 2;
        int endLen = length - startLen - headerLen;
        printGreenLine(repeatingString('-', length));
        printGreenLine(repeatingString('-', startLen) + header + repeatingString('-', endLen));
        printGreenLine(repeatingString('-', length));
    }

    private String getPadding(int level) {
        return repeatingString(' ', level * 3);
    }

    private String repeatingString(char c, int length) {
        char[] chars = new char[length];
        Arrays.fill(chars, c);
        return new String(chars);
    }

    private void printYellowLine(String line) {
        printColoredLine(line, ConsoleColors.YELLOW);
    }
    private void printRedLine(String line) {
        printColoredLine(line, ConsoleColors.RED);
    }
    private void printGreenLine(String line) {
        printColoredLine(line, ConsoleColors.GREEN);
    }

    private void printColoredLine(String line, String color) {
        System.out.println(color + line + ConsoleColors.RESET);
    }

    private void printYellow(String line) {
        printColored(line, ConsoleColors.YELLOW);
    }
    private void printRed(String line) {
        printColored(line, ConsoleColors.RED);
    }
    private void printGreen(String line) {
        printColored(line, ConsoleColors.GREEN);
    }

    private void printColored(String line, String color) {
        System.out.print(color + line + ConsoleColors.RESET);
    }

    @Override
    public void close() {
        scanner.close();
    }
}
