package com.example.chat2.server.domain;

import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class ServerEventsProcessor implements Consumer<ServerEvent> {

    private final ServerWorkers serverWorkers;

    @Override
    public void accept(ServerEvent event) {
        switch (event.getType()) {
            case MESSAGE_RECEIVED -> serverWorkers
                    .broadcast(event.getMessage(), event.getRoom(), event.getSource());
            case FILE_RECEIVED -> serverWorkers
                    .broadcastFile(event.getMessage(), event.getRoom(), event.getSource());
            case CONNECTION_CLOSED -> serverWorkers.remove(event.getSource().getUser());
        }
    }

}
