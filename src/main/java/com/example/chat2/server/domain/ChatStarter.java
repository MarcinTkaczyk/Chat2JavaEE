package com.example.chat2.server.domain;


import com.example.chat2.server.adapters.jms.JmsServerWriter;
import com.example.chat2.server.ports.out.ChatRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.naming.NamingException;

@Log
@ApplicationScoped
public class ChatStarter {


    private ChatRepository chatRepository;
    private ChatRoomManager chatRoomManager;
    private EventsBus eventsBus;

    public ChatStarter(){}

    @Inject
    public ChatStarter(ChatRepository chatRepository, ChatRoomManager chatRoomManager, EventsBus eventsBus){
        this.chatRepository = chatRepository;
        this.chatRoomManager = chatRoomManager;
        this.eventsBus = eventsBus;
    }

//    private void forceEagerInitialization(@Observes Startup startup) {
//    }

    @PostConstruct
    public void postConstruct(
            @Observes @Initialized( ApplicationScoped.class ) Object init
    ){
        log.info("Post construct");
        start();
    }

    @SneakyThrows
    @Singleton
    @Produces
    public ServerWorkers serverWorkers(){
        return new SynchronizedOnMethodServiceWorkers(new HashSetServerWorkers(new JmsServerWriter()));
    }
    @SneakyThrows
    public void start() {
        System.out.println("-------------Starting----------------");
        eventsBus.addConsumer(new ServerEventsLogger());
        eventsBus.addConsumer(new MessagesHistoryLogger());

        var historyLogger = new ChatHistoryLogger(chatRepository);
        var server = new ChatServer(serverWorkers(), eventsBus, chatRoomManager, historyLogger, new JmsServerWriter());

        try {
            server.initiate();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

    }
}
