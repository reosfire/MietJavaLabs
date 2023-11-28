package ru.reosfire.special.frames;

import ru.reosfire.special.components.MoleculeConstructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
    private final static int HEIGHT = 1080;
    private final static int WIDTH = 1920;

    MoleculeConstructor moleculeConstructor = new MoleculeConstructor();

    public MainFrame() throws HeadlessException {
        setJMenuBar(createMenuBar());


        setSize(WIDTH, HEIGHT);
        add(moleculeConstructor);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem saveItem = new JMenuItem(new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        JMenuItem saveAsItem = new JMenuItem(new AbstractAction("Save as") {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        JMenuItem loadItem = new JMenuItem(new AbstractAction("Load") {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(loadItem);

        menuBar.add(fileMenu);

        return menuBar;
    }

}
