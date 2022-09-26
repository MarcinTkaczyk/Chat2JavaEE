package com.example.chat2.server.domain;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class ChatRoom {

    private final String roomName;
    @Getter
    private Set<String> usersInRoom;

    public ChatRoom(String roomName) {
        this.roomName = roomName;
        usersInRoom = new HashSet<>();
    }

    public void addUser(String user){
        usersInRoom.add(user);};

    public void removeUser(String user){
        usersInRoom.remove(user);
    };

    public String getRoomName(){
        return this.roomName;
    }

    public boolean checkIfRoomEmpty(){
        return usersInRoom.isEmpty();
    }

}
