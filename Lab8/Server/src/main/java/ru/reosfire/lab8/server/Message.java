package ru.reosfire.lab8.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Message {
    private static final Charset charset = StandardCharsets.UTF_8;

    public final String senderName;
    public final String content;

    public Message(String senderName, String content) {
        this.senderName = senderName;
        this.content = content;
    }

    public byte[] toBytes() {
        byte[] nameBytes = toBytes(senderName);
        byte[] contentBytes = toBytes(content);

        ByteBuffer buffer = ByteBuffer.allocate(nameBytes.length + contentBytes.length);

        buffer.put(nameBytes);
        buffer.put(contentBytes);

        return buffer.array();
    }

    public void send(BufferedOutputStream stream) throws IOException {
        byte[] bytes = toBytes();
        ByteBuffer buffer = ByteBuffer.allocate(4 + bytes.length);

        buffer.putInt(bytes.length);
        buffer.put(bytes);

        stream.write(buffer.array());
    }

    @Override
    public String toString() {
        return senderName + ": " + content;
    }

    public static Message fromBytes(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        int nameLength = buffer.getInt();
        byte[] nameBytes = new byte[nameLength];
        buffer.get(nameBytes);

        int contentLength = buffer.getInt();
        byte[] contentBytes = new byte[contentLength];
        buffer.get(contentBytes);

        return new Message(new String(nameBytes, charset), new String(contentBytes, charset));
    }

    public static Message receive(BufferedInputStream stream) throws IOException {
        byte[] sizeBytes = new byte[4];
        int read = stream.read(sizeBytes);
        if (read != 4)
            throw new RuntimeException("Incorrect message format: " + read + " size bytes received instead of 4");

        int size = ByteBuffer.wrap(sizeBytes).getInt();

        byte[] messageBytes = new byte[size];
        read = stream.read(messageBytes);
        if (read != size)
            throw new RuntimeException("Incorrect message format: " + read + " message bytes received instead of " + size);

        return fromBytes(messageBytes);
    }

    private static byte[] toBytes(String string) {
        byte[] bytes = string.getBytes(charset);
        ByteBuffer buffer = ByteBuffer.allocate(4 + bytes.length);

        buffer.putInt(bytes.length);
        buffer.put(bytes);

        return buffer.array();
    }
}
