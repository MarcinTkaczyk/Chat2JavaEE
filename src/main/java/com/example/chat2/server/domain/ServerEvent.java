package com.example.chat2.server.domain;

import com.example.chat2.server.ports.model.AbstractChatMessage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ServerEvent{

    private final ServerEventType type;
    private Worker source;
    AbstractChatMessage message;
    ChatRoom room;
    String author;
    String payload;

    public static class ServerEventBuilder{
        public ServerEventBuilder(){}
    }
    }

