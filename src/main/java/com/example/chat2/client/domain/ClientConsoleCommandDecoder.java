package com.example.chat2.client.domain;


import com.example.chat2.client.adapters.RestClient;
import com.example.chat2.server.ports.model.*;
import lombok.RequiredArgsConstructor;

import java.io.*;
import java.util.Map;

import static java.util.Map.entry;

@RequiredArgsConstructor
public class ClientConsoleCommandDecoder {

    private final String ROOMCHANGEPHRASE = "ROOM>";
    private final String HISTORYREQUESTPHRASE = "HISTORY>";
    private final String FILESENDREQUEST = "FILE>";

    private final String user;

    public AbstractChatMessage decodeAndPrepare(String text) {

         if(text.contains(ROOMCHANGEPHRASE))
        {
            String choosenRoom = text.substring(ROOMCHANGEPHRASE.length());
            return new CommandMessage.CommandMessageBuilder()
                    .command(Command.ROOMCHOICE)
                    .payload(Map.ofEntries(entry("room", choosenRoom)))
                    .build();
        }
        if(text.contains(HISTORYREQUESTPHRASE))
        {
            String choosenRoom = text.substring(HISTORYREQUESTPHRASE.length());
            return new CommandMessage.CommandMessageBuilder()
                    .command(Command.HISTORYREQUEST)
                    .payload(Map.ofEntries(
                            entry("room", choosenRoom),
                            entry("user", user)))
                    .build();

        }
        if(text.contains(FILESENDREQUEST))
        {
            String filePath = text.substring(FILESENDREQUEST.length());
            File file = new File(filePath);
            long length = file.length();
            byte[] bytes = new byte[(int)length];
            try {
                InputStream in = new FileInputStream(file);
                in.read(bytes);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return new FileMessage.FileMessageBuilder()
                    .file(bytes)
                    .source(user)
                    .name(file.getName())
                    .build();
        }

        else
        {
            return new TextMessage.TextMessageBuilder()
                    .author(user)
                    .text(text)
                    .build();
        }
}
}
