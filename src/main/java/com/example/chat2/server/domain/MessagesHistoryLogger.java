package com.example.chat2.server.domain;

import lombok.extern.java.Log;
import java.util.function.Consumer;
import static com.example.chat2.server.domain.ServerEventType.MESSAGE_RECEIVED;

@Log
public class MessagesHistoryLogger implements Consumer<ServerEvent> {

    @Override
    public void accept(ServerEvent event) {
        if (event.getType().equals(MESSAGE_RECEIVED)) {
            log.info("New message: " + event.getType().toString());
        }
    }

}
