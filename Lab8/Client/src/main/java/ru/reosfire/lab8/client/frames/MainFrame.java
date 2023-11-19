package ru.reosfire.lab8.client.frames;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

public class MainFrame extends JFrame {
    private final static int HEIGHT = 1080;
    private final static int WIDTH = 1920;

    private final static int COUNT = 10;

    public MainFrame() throws HeadlessException, IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1", 25565));
        new Thread(() -> {
            byte[] buff = new byte[3];
            while (true) {
                try {
                    int read = socket.getInputStream().read(buff);
                    System.out.println(buff[0] + " " + buff[1] + " " + buff[2]);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        getRootPane().setBackground(Color.CYAN);
        setSize(WIDTH, HEIGHT);
        setLayout(null);
    }

    private List<Message>



    private void makeDialog(String title, String content) {
        Dialog dialog = new Dialog(this, title, true);
        dialog.setLayout(new FlowLayout());

        Label label = new Label(content);
        label.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        dialog.add(label);

        Button restart = new Button("OK");
        restart.addActionListener(it -> dialog.setVisible(false));
        dialog.add(restart);

        dialog.setSize(350, 150);
        dialog.setVisible(true);
    }
}
