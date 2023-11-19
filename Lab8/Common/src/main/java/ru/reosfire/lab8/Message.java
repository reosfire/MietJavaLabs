package ru.reosfire.lab8;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class Message {
    public final String senderName;
    public final String content;

    public Message(String senderName, String content) {
        this.senderName = senderName;
        this.content = content;
    }

    public void sendTo(OutputStreamWriter writer) throws IOException {
        writer.write(Integer.toString(senderName.length()));
        writer.write(senderName);

        writer.write(Integer.toString(content.length()));
        writer.write(content);
    }
}
