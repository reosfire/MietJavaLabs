package ru.reosfire.lab8.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    private static final List<Message> messagesHistory = new CopyOnWriteArrayList<>();
    private static final List<Socket> activeClients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress("127.0.0.1", 25565));

            while (true) {
                acceptNext(serverSocket);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while creating server socket", e);
        }
    }

    private static void acceptNext(ServerSocket serverSocket) {
        try {
            Socket acceptedSocket = serverSocket.accept();
            try (OutputStreamWriter writer = new OutputStreamWriter(acceptedSocket.getOutputStream())) {
                sendMessagesHistory(writer);
            } catch (Exception e) {
                throw new RuntimeException("Error while sending messages history", e);
            }

            new Thread(() -> {
                while (true) {
                    try {
                        acceptedSocket.getOutputStream().write(new byte[] { 0, 0, 1 });
                        System.out.println("writed");
                    } catch (IOException e) {
                        System.out.println(e);
                        throw new RuntimeException(e);
                    }
                }
            }).start();

            activeClients.add(acceptedSocket);
        } catch (Exception e) {
            System.out.println("Error while accepting next socket connection");
            System.out.println(e);
        }
    }

    private static void sendMessagesHistory(OutputStreamWriter writer) throws IOException {
        writer.write(Integer.toString(messagesHistory.size()));
        for (Message message : messagesHistory) {
            message.sendTo(writer);
        }
    }
}