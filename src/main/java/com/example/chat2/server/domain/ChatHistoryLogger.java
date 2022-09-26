package com.example.chat2.server.domain;



import com.example.chat2.server.ports.out.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Log
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ChatHistoryLogger implements HistoryLogger{
    private Map<ChatRoom, ChatLog> chatLogs = new HashMap();
    private final ChatRepository chatRepository;

    @Override
    public void log(ChatRoom room, String user, String text) {
        chatLogs.get(room).log(text);
    }

    @Override
    public List<String> chatHistory(String user, String room) {

        List<ChatLog>userChatHistory = chatRepository.getByUserAndRoom(user, room);
        List<String> userChatHistoryFormatted = new ArrayList<>();
        if(!userChatHistory.isEmpty()){
        userChatHistory.sort((log1, log2) -> log1.getCloseDate().compareTo(log2.getCloseDate()));

        for(ChatLog log : userChatHistory){
            userChatHistoryFormatted.add("Closed on " + log.getCloseDate().toString());
            for(String line : log.getMessages()){
                userChatHistoryFormatted.add(line);
            }
        }
    }else{
            userChatHistoryFormatted.add("No history found");
        }

        return userChatHistoryFormatted;
    }

    @Override
    public void registerRoom(ChatRoom room) {
        if(!chatLogs.containsKey(room)){
        chatLogs.put(room, new ChatLog(room.getRoomName()));
        }
    }

    @Override
    public void registerUser(ChatRoom room, String user) {
        chatLogs.get(room).addUser(user);
    }

    @Override
    public void save(ChatRoom room) {
        ChatLog chatLogToSave = chatLogs.get(room);

        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String fileNameDate = dateFormat.format(date);
        chatLogToSave.setCloseDate(date);

        chatRepository.save(chatLogToSave);

        chatLogs.remove(room);
        log.info("closing room: "+ room);
    }
}
