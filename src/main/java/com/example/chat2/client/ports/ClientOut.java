package com.example.chat2.client.ports;


import com.example.chat2.server.ports.model.AbstractChatMessage;

public interface ClientOut {
    public void write(AbstractChatMessage message);
}
