package com.example.chat2.server.ports.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FileIdMessage extends AbstractChatMessage{
    String id;
    String fileName;

    public static class FileIdMessageBuilder{
        public FileIdMessageBuilder(){}
    }
}
