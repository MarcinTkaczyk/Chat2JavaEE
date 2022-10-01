package com.example.chat2.server.domain;

import com.example.chat2.server.ports.model.AbstractChatMessage;
import lombok.RequiredArgsConstructor;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@RequiredArgsConstructor
public class ServerMessageListener implements MessageListener {

    private final IncomingMessageService incomingMessageService;

    @Override
    public void onMessage(Message message) {
        try {
            var payload = message.getBody(AbstractChatMessage.class);
            incomingMessageService.decodeMessage(payload);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
