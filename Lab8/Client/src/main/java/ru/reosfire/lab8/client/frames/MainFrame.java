package ru.reosfire.lab8.client.frames;

import ru.reosfire.lab8.client.DialogView;
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
    private static final int FONT_SIZE = 20;
    private static final Font FONT = new Font("TimesRoman", Font.PLAIN, FONT_SIZE);
    private static final Color MESSAGE_BACKGROUND_COLOR = new Color(0, 136, 204);

    private final static int HEIGHT = 1080;
    private final static int WIDTH = 1920;
    private final Socket socket = new Socket();
    private final BufferedInputStream input;
    private final BufferedOutputStream output;

    private final String senderName = "reosfire";

    public MainFrame() throws HeadlessException, IOException {
        socket.connect(new InetSocketAddress("127.0.0.1", 25565));

        input = new BufferedInputStream(socket.getInputStream());
        output = new BufferedOutputStream(socket.getOutputStream());


        List<Message> messagesHistory = getMessagesHistory(input);
        for (Message message : messagesHistory) {
            System.out.println(message);
        }

        DialogView dialogView = new DialogView(senderName);
        dialogView.setContent(messagesHistory);
        new Thread(() -> {
            while (true) {
                try {
                    Message message = Message.receive(input);
                    messagesHistory.add(message);
                    System.out.println(message);

                    dialogView.setContent(messagesHistory);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        setSize(WIDTH, HEIGHT);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1;

        gridBagConstraints.weighty = 1;
        gridBagConstraints.gridy = 0;
        mainPanel.add(dialogView, gridBagConstraints);

        gridBagConstraints.weighty = 0;
        gridBagConstraints.gridy = 1;
        mainPanel.add(messageSendingPanel(), gridBagConstraints);

        add(mainPanel);
    }

    private JPanel messageSendingPanel() {
        JPanel messageSendPanel = new JPanel();
        messageSendPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1;

        gridBagConstraints.weightx = 0.93;
        gridBagConstraints.gridx = 0;
        JTextField sendTextField = createSendTextField();
        messageSendPanel.add(sendTextField, gridBagConstraints);

        gridBagConstraints.weightx = 0.07;
        gridBagConstraints.gridx = 1;
        JButton sendButton = createSendButton();
        messageSendPanel.add(sendButton, gridBagConstraints);

        sendButton.addActionListener(it -> {
            String message = sendTextField.getText();
            if (message.trim().isEmpty()) return;
            try {
                new Message(senderName, message).send(output);
                output.flush();
                sendTextField.setText("");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        sendTextField.addActionListener(it -> {
            String message = sendTextField.getText();
            if (message.trim().isEmpty()) return;
            try {
                new Message(senderName, message).send(output);
                output.flush();
                sendTextField.setText("");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return messageSendPanel;
    }
    private JButton createSendButton() {
        JButton sendMessageButton = new JButton("Send");
        sendMessageButton.setFont(FONT);
        sendMessageButton.setBackground(MESSAGE_BACKGROUND_COLOR);
        sendMessageButton.setForeground(Color.WHITE);
        return sendMessageButton;
    }

    private JTextField createSendTextField() {
        JTextField sendMessageField = new JTextField();
        sendMessageField.setFont(FONT);
        return sendMessageField;
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
