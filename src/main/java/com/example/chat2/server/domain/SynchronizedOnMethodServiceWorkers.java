package com.example.chat2.server.domain;

import com.example.chat2.server.ports.model.AbstractChatMessage;
import lombok.RequiredArgsConstructor;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@RequiredArgsConstructor
class SynchronizedOnMethodServiceWorkers implements ServerWorkers {

    private final ServerWorkers serverWorkers;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public synchronized void add(String name, Worker worker) {

        serverWorkers.add(name, worker);

    }

    @Override
    public synchronized void remove(String worker) {

        serverWorkers.remove(worker);

    }

    @Override
    public synchronized void broadcast(AbstractChatMessage message, ChatRoom roomToBroadcast, Worker sender) {

        serverWorkers.broadcast(message, roomToBroadcast, sender);

    }

    @Override
    public synchronized Worker getWorker(String name) {

        var worker = serverWorkers.getWorker(name);

        return worker;
    }

    @Override
    public synchronized void broadcastFile(AbstractChatMessage message, String source) {

        serverWorkers.broadcastFile(message, source);
    }


}
