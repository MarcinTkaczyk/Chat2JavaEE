package com.example.chat2.server.ports.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
public class ChatHistoryMessage extends AbstractChatMessage implements Serializable {
    private List<String> chatLogs;

    public static class ChatHistoryMessageBuilder{
        public ChatHistoryMessageBuilder(){}
    }

}
