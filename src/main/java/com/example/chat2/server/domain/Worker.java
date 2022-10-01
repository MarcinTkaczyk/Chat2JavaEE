package com.example.chat2.server.domain;

import com.example.chat2.server.ports.model.*;
import com.example.chat2.server.ports.out.ServerOut;
import lombok.Getter;
import javax.naming.NamingException;
import java.util.Set;

import static com.example.chat2.server.domain.ServerEventType.*;


class Worker{

    private final EventsBus eventsBus;
    private final HistoryLogger historyLogger;
    private ChatRoom currentRoom;
    private Boolean close = false;
    private ChatRoomManager chatRoomManager;
    private ServerOut serverOut;
    @Getter
    private String user;

    Worker(String name, EventsBus eventsBus, ChatRoomManager chatRoomManager, HistoryLogger historyLogger, ServerOut serverOut){

        this.eventsBus = eventsBus;
        this.user = name;
        this.chatRoomManager = chatRoomManager;
        currentRoom = chatRoomManager.getDefaultRoom();
        this.historyLogger = historyLogger;
        this.serverOut = serverOut;
    }

    public void start(String user){
        currentRoom.addUser(user);
        historyLogger.registerRoom(currentRoom);
        historyLogger.registerUser(currentRoom, user);
    }

    public void retrieveHistory(String room){
        var chatLogs = historyLogger.chatHistory(user, room);
        var result = new ChatHistoryMessage.ChatHistoryMessageBuilder()
                .chatLogs(chatLogs).build();
        send(result);
    }

    public void close(){
        close = true;
        onInputClose();
    }

    public void processFileMessage(FileMessage fileMessage){
        eventsBus.publish(ServerEvent.builder()
                .type(FILE_RECEIVED)
                .room(currentRoom)
                .source(this)
                .message(fileMessage)
                .build());
    }

    public void processTextMessage(TextMessage textMessage){
        String payload = user + ": " + textMessage.getText();
        historyLogger.log(currentRoom, user, payload);
        eventsBus.publish(ServerEvent.builder()
                .type(MESSAGE_RECEIVED)
                .message(textMessage)
                .source(this)
                .room(currentRoom)
                .build());
    }

    public void changeRoom(String room){
        ChatRoom newRoom;
        if(chatRoomManager.chatRoomExists(room)){
            newRoom = chatRoomManager.getRoomByName(room);
        } else {
            newRoom = chatRoomManager.openChatRoom(room);
        }
        chatRoomManager.removeUser(currentRoom, user, currentRoom->historyLogger.save(currentRoom));
        newRoom.addUser(user);
        currentRoom = newRoom;
        historyLogger.registerRoom(currentRoom);
        historyLogger.registerUser(currentRoom, user);
        send(new TextMessage.TextMessageBuilder()
                .text("You are now in the room : " + currentRoom.getRoomName())
                .author("System")
                .build());
    }

    private void onInputClose() {
        chatRoomManager.removeUser(currentRoom, user, currentRoom->historyLogger.save(currentRoom));
        eventsBus.publish(ServerEvent.builder()
                .type(CONNECTION_CLOSED)
                .source(this)
                .build());
    }

    void send(AbstractChatMessage message) {
        try {
            message.setRecipients(Set.of(user));
            serverOut.write(message);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    void processIncomingFileMessage(AbstractChatMessage message){
        var room = currentRoom;

    }

    ChatRoom getWorkerRoom(){
        return currentRoom;
    }
}
