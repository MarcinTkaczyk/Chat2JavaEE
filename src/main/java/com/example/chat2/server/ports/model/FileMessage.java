package com.example.chat2.server.ports.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
public class FileMessage extends AbstractChatMessage implements Serializable {
    String source;
    String name;
    String room;
    byte[] file;

    public static class FileMessageBuilder{
        public FileMessageBuilder(){};
    }
}
