package ru.reosfire.lab8.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    private static final List<Message> messagesHistory = new CopyOnWriteArrayList<>();
    private static final List<Socket> activeClients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        messagesHistory.add(new Message("reosfire", "Привет мир!"));
        messagesHistory.add(new Message("reosfire", "Привет мир!"));
        messagesHistory.add(new Message("reosfire", "Hello world!"));
        messagesHistory.add(new Message("reosfire", "Привет мир!"));
        messagesHistory.add(new Message("reosfire", "Привет мир!"));

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
            try {
                BufferedOutputStream stream = new BufferedOutputStream(acceptedSocket.getOutputStream());
                sendMessagesHistory(stream);
            } catch (Exception e) {
                throw new RuntimeException("Error while sending messages history", e);
            }

            runClientLoop(acceptedSocket);

        } catch (Exception e) {
            System.out.println("Error while accepting next socket connection");
            System.out.println(e);
        }
    }

    private static void runClientLoop(Socket acceptedSocket) {
        new Thread(() -> {
            activeClients.add(acceptedSocket);

            boolean running = true;
            while (running) {
                try {
                    BufferedInputStream input = new BufferedInputStream(acceptedSocket.getInputStream());
                    Message message = Message.receive(input);
                    messagesHistory.add(message);

                    broadcastMessage(message);

                    System.out.println("[Message received] " + message);
                } catch (Exception e) {
                    System.out.println("Disconnected");
                    activeClients.remove(acceptedSocket);
                    running = false;
                }
            }
        }).start();
    }

    public static void broadcastMessage(Message message) {
        for (Socket activeClient : activeClients) {
            try {
                sendSingleMessage(activeClient, message);
            } catch (IOException e) {
                activeClients.remove(activeClient);
            }
        }
    }
    private static void sendSingleMessage(Socket client, Message message) throws IOException {
        BufferedOutputStream clientStream = new BufferedOutputStream(client.getOutputStream());
        message.send(clientStream);

        clientStream.flush();
    }



    private static void sendMessagesHistory(BufferedOutputStream stream) throws IOException {
        stream.write(ByteBuffer.allocate(4).putInt(messagesHistory.size()).array());

        for (Message message : messagesHistory) {
            message.send(stream);
        }

        stream.flush();
    }
}