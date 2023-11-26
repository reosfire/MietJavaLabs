package ru.reosfire.special;

import ru.reosfire.special.frames.MainFrame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setTitle("Raises");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}