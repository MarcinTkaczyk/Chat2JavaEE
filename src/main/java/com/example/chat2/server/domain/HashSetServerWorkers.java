package com.example.chat2.server.domain;




import com.example.chat2.server.ports.model.AbstractChatMessage;
import com.example.chat2.server.ports.out.ServerOut;

import java.util.HashMap;
import java.util.Map;


class HashSetServerWorkers implements ServerWorkers {

    private ServerOut serverOut;
    private MessageBroadcaster messageBroadcaster;

    public HashSetServerWorkers(ServerOut serverOut) {
        this.serverOut = serverOut;
        messageBroadcaster = new MessageBroadcaster(serverOut);
    }

    private final Map<String, Worker> workers = new HashMap<>();


    @Override
    public void add(String name, Worker worker) {
        workers.put(name, worker);
    }

    @Override
    public void remove(String name) {
        workers.remove(name);
    }

    @Override
    public void broadcast(AbstractChatMessage message, ChatRoom roomToBroadcast, Worker sender) {
        messageBroadcaster.broadcast(message, roomToBroadcast);
    }

    @Override
    public Worker getWorker(String name) {
        return workers.get(name);
    }

    @Override
    public void broadcastFile(AbstractChatMessage message, ChatRoom room, Worker source) {

    }

}
