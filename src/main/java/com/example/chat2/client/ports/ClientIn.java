package com.example.chat2.client.ports;


import com.example.chat2.server.ports.model.AbstractChatMessage;

public interface ClientIn {
    public void onMessage(AbstractChatMessage abstractChatMessage);
}
