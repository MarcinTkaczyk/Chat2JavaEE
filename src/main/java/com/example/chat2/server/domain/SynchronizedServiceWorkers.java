package com.example.chat2.server.domain;


import com.example.chat2.server.ports.model.AbstractChatMessage;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@RequiredArgsConstructor
class SynchronizedServiceWorkers implements ServerWorkers {

    private final ServerWorkers serverWorkers;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void add(String name, Worker worker) {
        lock.writeLock().lock();
        serverWorkers.add(name, worker);
        lock.writeLock().unlock();
    }

    @Override
    public void remove(String worker) {
        lock.writeLock().lock();
        serverWorkers.remove(worker);
        lock.writeLock().unlock();
    }

    @Override
    public void broadcast(AbstractChatMessage message, ChatRoom roomToBroadcast, Worker sender) {
        lock.readLock().lock();
        serverWorkers.broadcast(message, roomToBroadcast, sender);
        lock.readLock().unlock();
    }

    @Override
    public Worker getWorker(String name) {
        lock.readLock();
        var worker = serverWorkers.getWorker(name);
        lock.readLock().unlock();
        return worker;
    }

    @Override
    public void broadcastFile(AbstractChatMessage message, ChatRoom room, Worker source) {
        lock.readLock().lock();
        serverWorkers.broadcastFile(message, room, source);
        lock.readLock().unlock();
    }


}
