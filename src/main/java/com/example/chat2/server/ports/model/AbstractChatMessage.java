package com.example.chat2.server.ports.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
public class AbstractChatMessage implements Serializable {
    @Setter
    String source;
    @Setter
    Set<String> recipients;
}
