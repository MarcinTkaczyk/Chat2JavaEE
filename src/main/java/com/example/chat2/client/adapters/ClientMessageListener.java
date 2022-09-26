package com.example.chat2.client.adapters;

import com.example.chat2.client.ports.ClientIn;
import com.example.chat2.server.ports.model.AbstractChatMessage;
import lombok.RequiredArgsConstructor;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@RequiredArgsConstructor
public class ClientMessageListener implements MessageListener {

    private final ClientIn clientIn;
    @Override
    public void onMessage(Message message) {
        try {
            var payload = message.getBody(AbstractChatMessage.class);
            clientIn.onMessage(payload);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
