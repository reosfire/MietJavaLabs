package ru.reosfire.lab8.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try (ServerSocket socket = new ServerSocket()) {
            socket.bind(new InetSocketAddress("127.0.0.1", 25565));

            while (true) {
                Socket accepted = socket.accept();
                System.out.println(socket.isBound());
                new Thread(() -> {
                    while (true) {
                        try {
                            accepted.getOutputStream().write(new byte[] { 0, 0, 1 });
                            System.out.println("writed");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}