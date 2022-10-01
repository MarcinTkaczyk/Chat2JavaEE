package com.example.chat2.client.domain;

import com.example.chat2.client.ports.ClientIn;
import com.example.chat2.client.ports.FileClient;
import com.example.chat2.server.ports.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.io.File;

@Log
@AllArgsConstructor
public class ClientIncomingMessageService implements ClientIn {

    private File downloadLocation = null;
    private final String clientId;
    private final FileClient fileClient;


    @Override
    public void onMessage(AbstractChatMessage message) {

            if(message.getRecipients().contains(clientId)) {

                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    ClientService.displayClientMessage(textMessage, clientId);
                }
                if (message instanceof ChatHistoryMessage) {
                    ChatHistoryMessage chatHistoryMessage = (ChatHistoryMessage) message;
                    ClientService.displayClientHistory(chatHistoryMessage);
                }
                if (message instanceof FileMessage) {
                    FileMessage fileMessage = (FileMessage) message;
                    ClientService.downloadFile(fileMessage, downloadLocation);
                }
                if (message instanceof FileIdMessage){
                    FileIdMessage fileIdMessage = (FileIdMessage) message;
                    if( !fileIdMessage.getSource().equals(clientId) ) {
                        log.info("Received file: "+ fileIdMessage.getFileName() + " from: " +
                                fileIdMessage.getSource() + ". Downloading...");
                        fileClient.getFile(fileIdMessage.getId(), fileIdMessage.getFileName(), downloadLocation);
                    }
                }

            }

    }

}
