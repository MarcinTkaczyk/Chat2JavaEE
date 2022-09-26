package com.example.chat2.server.domain;


import com.example.chat2.server.ports.model.AbstractChatMessage;
import com.example.chat2.server.ports.out.ServerOut;
import lombok.AllArgsConstructor;

import javax.naming.NamingException;

@AllArgsConstructor
public class MessageBroadcaster {

    private final ServerOut serverOut;

    public void broadcast(AbstractChatMessage message, ChatRoom roomToBroadcast){
        try {
            var users = roomToBroadcast.getUsersInRoom();
            message.setRecipients(users);
            serverOut.write(message);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}
