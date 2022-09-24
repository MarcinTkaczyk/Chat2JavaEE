package com.example.chat2.server.ports.out;

import com.example.chat2.server.domain.ChatLog;

import java.util.List;

public interface ChatRepository {

    public ChatLog save(ChatLog chatLog);
    public List<ChatLog> getByUser(String user);
    public List<ChatLog> getByUserAndRoom(String user, String room);
}
