package com.example.chat2.server.ports.model;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class TextMessage extends AbstractChatMessage implements Serializable {
    private String author;
    private String text;

    public static class TextMessageBuilder {
        public TextMessageBuilder() {
        }
    }
}
