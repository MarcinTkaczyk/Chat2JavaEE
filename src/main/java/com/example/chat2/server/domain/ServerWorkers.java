package com.example.chat2.server.domain;


import com.example.chat2.server.ports.model.AbstractChatMessage;

interface ServerWorkers {

    void add(String name, Worker worker);

    void remove(String name);

    void broadcast(AbstractChatMessage message, ChatRoom roomToBroadcast, Worker sender);

    Worker getWorker(String name);

    void broadcastFile(AbstractChatMessage message, ChatRoom room, Worker source);
}
