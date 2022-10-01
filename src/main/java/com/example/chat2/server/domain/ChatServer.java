package com.example.chat2.server.domain;


import com.example.chat2.server.ports.out.ServerOut;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import javax.naming.NamingException;

import static com.example.chat2.server.domain.ServerEventType.CONNECTION_ACCEPTED;
import static com.example.chat2.server.domain.ServerEventType.SERVER_STARTED;


@Log
@RequiredArgsConstructor
public class ChatServer {

    private final ServerWorkers serverWorkers;
    private final EventsBus eventsBus;
    private final ChatRoomManager chatRoomManager;
    private final HistoryLogger historyLogger;
    private final ServerOut serverOut;


    private void start() throws NamingException {
        log.info("starting server");
        eventsBus.addConsumer(new ServerEventsProcessor(serverWorkers));
        eventsBus.publish(ServerEvent.builder().type(SERVER_STARTED).build());

       var listener = new Thread( new JmsServerListener(new IncomingMessageService(this)));
       listener.start();
    }

    public Worker getWorker(String name){
        return serverWorkers.getWorker(name);
    }

    public void createWorker(String name) {
        var worker = new Worker(name, eventsBus, chatRoomManager, historyLogger, serverOut);
        eventsBus.publish(ServerEvent.builder().type(CONNECTION_ACCEPTED).build());
        serverWorkers.add(name, worker);
        worker.start(name);

    }

    public void initiate() throws NamingException {
        start();
    }



}
