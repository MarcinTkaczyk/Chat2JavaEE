package com.example.chat2.server.domain;

import com.example.chat2.server.ports.model.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IncomingMessageService{

    private final ChatServer chatServer;

public void decodeMessage(AbstractChatMessage message){
    String worker = message.getSource();
    if(message instanceof CommandMessage) {
        CommandMessage command = (CommandMessage) message;
        switch (command.getCommand()){
            case STARTSESSION -> {
                chatServer.createWorker(command.getPayload().get("name"));
            }
            case ROOMCHOICE -> {
                chatServer.getWorker(worker).changeRoom(command.getPayload().get("room"));
            }
            case HISTORYREQUEST -> {
                chatServer.getWorker(worker).retrieveHistory(command.getPayload().get("room"));
            }
            case CLOSESESSION -> {
                chatServer.getWorker(worker).close();
            }
        }
    }else if(message instanceof FileMessage){
        FileMessage fileMessage = (FileMessage) message;
        chatServer.getWorker(worker).processFileMessage(fileMessage);

    }else if(message instanceof TextMessage){
        TextMessage textMessage = (TextMessage) message;

        chatServer.getWorker(worker).processTextMessage(textMessage);
    }

}
}

