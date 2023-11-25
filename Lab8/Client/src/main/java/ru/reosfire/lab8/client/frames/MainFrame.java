package ru.reosfire.lab8.client.frames;

import ru.reosfire.lab8.client.DialogView;
import ru.reosfire.lab8.client.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
    private BufferedInputStream input;
    private BufferedOutputStream output;

    DialogView dialogView = new DialogView();
    List<Message> messagesHistory;

    ConnectionData defaultConnectionData = new ConnectionData("127.0.0.1:25565", "reosfire");

    public MainFrame() throws HeadlessException, IOException {
        reconnect();

        new Thread(() -> {
            while (true) {
                try {
                    Message message = Message.receive(input);
                    messagesHistory.add(message);
                    System.out.println(message);

                    dialogView.setContent(messagesHistory);
                } catch (IOException e) {
                    reconnect();
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

    private void reconnect() {
        while (true) {
            try {
                if (input != null) input.close();
                if (output != null) output.close();

                ConnectionData connectionData = makeConnectionDataDialog(defaultConnectionData);
                defaultConnectionData = connectionData;

                Socket socket = new Socket();

                socket.connect(new InetSocketAddress(connectionData.getIP(), connectionData.getPort()));
                dialogView.setSenderName(connectionData.login);

                input = new BufferedInputStream(socket.getInputStream());
                output = new BufferedOutputStream(socket.getOutputStream());

                messagesHistory = getMessagesHistory(input);
                for (Message message : messagesHistory) {
                    System.out.println(message);
                }
                dialogView.setContent(messagesHistory);

                return;
            } catch (Exception e) {
                System.out.println(e);
                makeDialog("Error", "Unable to connect to host. " + e.getMessage());
            }
        }
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

        ActionListener messageSendAction = it -> {
            String message = sendTextField.getText();
            if (message.trim().isEmpty()) return;
            try {
                new Message(dialogView.getSenderName(), message).send(output);
                output.flush();
                sendTextField.setText("");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };

        sendButton.addActionListener(messageSendAction);
        sendTextField.addActionListener(messageSendAction);

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


    private ConnectionData makeConnectionDataDialog(ConnectionData def) {
        JDialog dialog = new JDialog(this, "Connection Data", true);
        dialog.setLayout(new GridBagLayout());

        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0;

        constraints.gridx = 0;
        constraints.gridy = 0;
        JLabel hostLabel = new JLabel("Host: ");
        hostLabel.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        fieldsPanel.add(hostLabel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        JLabel loginLabel = new JLabel("Login: ");
        loginLabel.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        fieldsPanel.add(loginLabel, constraints);

        constraints.weightx = 1;

        constraints.gridx = 1;
        constraints.gridy = 0;
        JTextField hostField = new JTextField();
        hostField.setText(def.host);
        hostField.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        fieldsPanel.add(hostField, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        JTextField loginField = new JTextField();
        loginField.setText(def.login);
        loginField.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        fieldsPanel.add(loginField, constraints);


        constraints.gridx = 0;
        constraints.gridy = 0;
        dialog.add(fieldsPanel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        JButton confirmButton = new JButton("Confirm");
        confirmButton.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        dialog.add(confirmButton, constraints);

        ActionListener closeAndReturnAction = it -> {
            System.out.println("fffffffffffffff");
            if (hostField.getText().trim().isEmpty()) return;
            if (loginField.getText().trim().isEmpty()) return;
            dialog.setVisible(false);
        };

        confirmButton.addActionListener(closeAndReturnAction);
        hostField.addActionListener(closeAndReturnAction);
        loginField.addActionListener(closeAndReturnAction);

        dialog.setSize(350, 150);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        dialog.addWindowListener(new WindowCloser());
        dialog.setVisible(true);

        return new ConnectionData(hostField.getText(), loginField.getText());
    }

    private void makeDialog(String title, String content) {
        JDialog dialog = new JDialog(this, title, true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.gridx = 0;

        constraints.gridy = 0;
        JLabel label = new JLabel(content);
        label.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        dialog.add(label, constraints);

        constraints.gridy = 1;
        JButton ok = new JButton("OK");
        ok.addActionListener(it -> dialog.setVisible(false));
        dialog.add(ok, constraints);

        dialog.setSize(label.getPreferredSize().width + 40, 150);
        dialog.setVisible(true);
    }


    private static class WindowCloser implements WindowListener {
        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }

        @Override
        public void windowClosed(WindowEvent e) {
        }

        @Override
        public void windowIconified(WindowEvent e) {
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
        }

        @Override
        public void windowActivated(WindowEvent e) {
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
        }
    }
}
