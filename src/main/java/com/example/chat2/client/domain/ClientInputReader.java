package com.example.chat2.client.domain;

import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

@Log
public class ClientInputReader {

    private final Consumer<String> textConsumer;
    private BufferedReader reader;
    private Runnable onClose;
    private final String QUITPHRASE = "q";

    public ClientInputReader(InputStream inputStream, Consumer<String> textConsumer) {
        this.textConsumer = textConsumer;
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public void read() {
        String text;
        try {
            while (!(text = reader.readLine()).equals(QUITPHRASE) ) {
                textConsumer.accept(text);
            }
        } catch (IOException exception) {
            log.severe("Read message failed: " + exception.getMessage());
        } finally {
            log.info("closing loop");
//            if (onClose != null) {
//                onClose.run();
//            }
        }
    }

}
