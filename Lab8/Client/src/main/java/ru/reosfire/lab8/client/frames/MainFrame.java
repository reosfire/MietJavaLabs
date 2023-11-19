package ru.reosfire.lab8.client.frames;

import ru.reosfire.lab8.client.Message;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainFrame extends JFrame {
    private final static int HEIGHT = 1080;
    private final static int WIDTH = 1920;
    Socket socket = new Socket();

    public MainFrame() throws HeadlessException, IOException {
        socket.connect(new InetSocketAddress("127.0.0.1", 25565));

        BufferedInputStream input = new BufferedInputStream(socket.getInputStream());
        BufferedOutputStream output = new BufferedOutputStream(socket.getOutputStream());


        for (Message message : getMessagesHistory(input)) {
            System.out.println(message);
        }

        new Thread(() -> {
            while (true) {
                try {
                    Message message = Message.receive(input);
                    System.out.println(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        new Thread(() -> {
            int i = 0;
            while (true) {
                try {
                    new Message("heart beat", Integer.toString(i++)).send(output);
                    output.flush();

                    Thread.sleep(1000);
                } catch (InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        getRootPane().setBackground(Color.CYAN);
        setSize(WIDTH, HEIGHT);
        setLayout(null);
    }

    private List<Message> getMessagesHistory(BufferedInputStream stream) throws IOException {
        byte[] sizeBytes = new byte[4];
        int read = stream.read(sizeBytes);
        if (read != 4)
            throw new RuntimeException("Incorrect history format: " + read + " size bytes received instead of 4");

        int size = ByteBuffer.wrap(sizeBytes).getInt();

        List<Message> result = new CopyOnWriteArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(Message.receive(stream));
        }

        return result;
    }



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
