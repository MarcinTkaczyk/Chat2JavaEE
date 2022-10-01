package com.example.chat2.client.domain;


import com.example.chat2.client.adapters.JmsClientWriter;
import com.example.chat2.client.adapters.JmsListener;
import com.example.chat2.client.adapters.RestClient;
import com.example.chat2.client.ports.ClientIn;
import com.example.chat2.client.ports.ClientOut;
import com.example.chat2.client.ports.FileClient;
import com.example.chat2.server.commons.PropertiesLoader;
import com.example.chat2.server.ports.model.*;
import lombok.extern.java.Log;

import javax.naming.NamingException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;

@Log
public class ChatClient {

    private final Runnable readFromConsole;
    private final String name;
    ClientOut clientOut;
    FileClient fileClient;
    ClientIn clientIn;

    public ChatClient(String name, ClientIn clientIn, ClientOut clientOut, FileClient fileClient){

        this.name = name;
        this.clientOut = clientOut;
        this.fileClient = fileClient;
        this.clientIn = clientIn;


        readFromConsole = new Runnable() {
            @Override
            public void run() {

                new ClientInputReader(System.in, new Consumer<String>() {
                    @Override
                    public void accept(String text) {
                        AbstractChatMessage message = new ClientConsoleCommandDecoder(name).decodeAndPrepare(text);
                        message.setSource(name);
                        if(message instanceof FileMessage){
                            fileClient.postFile((FileMessage) message);
                        }else {
                            clientOut.write(message);
                        }
                    }
                }).read();
                System.exit(0);
            }
        };
    }

    private void start() {
        clientOut.write( new CommandMessage.CommandMessageBuilder()
                .command(Command.STARTSESSION)
                .payload(Map.ofEntries(Map.entry("name", name)))
                .build()
        );

        var consoleReader = new Thread(readFromConsole);
        consoleReader.setDaemon(false);
        consoleReader.start();
        ClientService.showMenu();
    }

    public static void main(String[] args) throws NamingException {
        String user = args[0];

        Properties properties = PropertiesLoader.loadProperties();
        File clientOutputFolder = new File(properties.getProperty("client.downloadLocation"),user);
        if(!clientOutputFolder.exists()){
            clientOutputFolder.mkdir();
        }
        ClientOut clientOut = new JmsClientWriter();
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                log.info("Closing connection with server");
                AbstractChatMessage exitMessage = new CommandMessage.CommandMessageBuilder()
                        .command(Command.CLOSESESSION)
                        .build();
                exitMessage.setSource(user);
                clientOut.write(exitMessage);
            }
        });
        FileClient fileClient = new RestClient();
        ClientIn clientIn = new ClientIncomingMessageService(clientOutputFolder, user, fileClient);
        JmsListener jmsListener = new JmsListener(clientIn);
        var jmsListenerThread = new Thread(jmsListener);
        jmsListenerThread.start();

        new ChatClient(args[0], clientIn, clientOut, fileClient).start();

    }

}
