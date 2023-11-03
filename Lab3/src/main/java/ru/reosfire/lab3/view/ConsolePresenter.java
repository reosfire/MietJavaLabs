package ru.reosfire.lab3.view;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConsolePresenter implements AutoCloseable {
    public final Scanner scanner = new Scanner(System.in);

    public EnclosureType readEnclosureType(String welcome) {
        String enclosureTypesString = Arrays.stream(EnclosureType.values()).map(Enum::name).collect(Collectors.joining(", "));
        while (true) {
            printWelcomeLine(welcome);
            String read = readNotEmptyString("Enter one of [" + enclosureTypesString + "] enclosure types: ");
            try {
                return EnclosureType.valueOf(read);
            } catch (Exception e) {
                printError(read + " is not one of [" + enclosureTypesString + "]");
            }
        }
    }

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



    public void printBlock(String block, int level) {
        for (String line : block.split("\n")) {
            printLine(getPadding(level) + line);
        }
    }



    //welcome
    public void printWelcome(String welcome) {
        printWelcome(welcome, 0);
    }
    public void printWelcome(String welcome, int level) {
        printPurple(getPadding(level) + welcome);
    }
    public void printWelcomeLine(String welcome) {
        printWelcomeLine(welcome, 0);
    }
    public void printWelcomeLine(String welcome, int level) {
        printPurpleLine(getPadding(level) + welcome);
    }



    //info/success/error
    public void printInfoLine(String info) {
        printInfoLine(info, 0);
    }
    public void printInfoLine(String info, int level) {
        printYellowLine(getPadding(level) + info);
    }

    public void printSuccess(String success) {
        printSuccess(success, 0);
    }
    public void printSuccess(String success, int level) {
        printGreenLine(getPadding(level) + success);
    }

    public void printError(String error) {
        printError(error, 0);
    }
    public void printError(String error, int level) {
        printRedLine(getPadding(level) + error);
    }



    //header
    public void printHeader(String header) {
        printHeader(header, 100);
    }
    public void printHeader(String header, int length) {
        int headerLen = header.length();
        int startLen = (length - headerLen) / 2;
        int endLen = length - startLen - headerLen;
        printBlueLine(repeatingString('-', length));
        printBlueLine(repeatingString('-', startLen) + header + repeatingString('-', endLen));
        printBlueLine(repeatingString('-', length));
    }



    //essentials
    public void printLine() {
        System.out.println();
    }
    public void printLine(String line) {
        System.out.println(line);
    }

    public void print(String line) {
        System.out.print(line);
    }

    public String repeatingString(char c, int length) {
        char[] chars = new char[length];
        Arrays.fill(chars, c);
        return new String(chars);
    }

    private String getPadding(int level) {
        return repeatingString(' ', level * 3);
    }


    //colored
    private void printYellowLine(String line) {
        printColoredLine(line, ConsoleColors.YELLOW);
    }
    private void printRedLine(String line) {
        printColoredLine(line, ConsoleColors.RED);
    }
    private void printGreenLine(String line) {
        printColoredLine(line, ConsoleColors.GREEN);
    }
    private void printPurpleLine(String line) {
        printColoredLine(line, ConsoleColors.PURPLE);
    }
    private void printBlueLine(String line) {
        printColoredLine(line, ConsoleColors.BLUE_BRIGHT);
    }
    private void printColoredLine(String line, String color) {
        printLine(color + line + ConsoleColors.RESET);
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
    private void printPurple(String line) {
        printColored(line, ConsoleColors.PURPLE);
    }
    private void printBlue(String line) {
        printColored(line, ConsoleColors.BLUE_BRIGHT);
    }
    private void printColored(String line, String color) {
        print(color + line + ConsoleColors.RESET);
    }

    @Override
    public void close() {
        scanner.close();
    }
}
