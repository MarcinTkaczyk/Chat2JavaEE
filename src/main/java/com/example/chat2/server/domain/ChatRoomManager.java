package com.example.chat2.server.domain;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@ApplicationScoped
public class ChatRoomManager {

    private final String DEFAULTROOM = "GENERAL";
    private List<ChatRoom> chatRooms;
    public ChatRoomManager() {
        chatRooms = new ArrayList<>();
        chatRooms.add(new ChatRoom(DEFAULTROOM));
    }

    public ChatRoom openChatRoom(String roomName){
        ChatRoom newRoom = new ChatRoom(roomName);
        chatRooms.add(newRoom);
        return newRoom;
    };
    public void closeChatroom(ChatRoom room){
        chatRooms.remove(room);
    };
     public List<ChatRoom> getChatRooms(){
         return chatRooms;
     }
     public boolean chatRoomExists(String name){
         for(ChatRoom room: chatRooms){
             if(room.getRoomName().equals(name)){
                 return true;
             }
         }
         return false;
     }

     public ChatRoom getRoomByName(String name){
         for(ChatRoom room: chatRooms){
             if(room.getRoomName().equals(name)){
                 return room;
             }
         }
         return null;
     }

     public ChatRoom getDefaultRoom(){
         if(!checkIfRoomExists(DEFAULTROOM)){
             chatRooms.add(new ChatRoom(DEFAULTROOM));
         }
         return getRoomByName(DEFAULTROOM);
     }

     public void removeUser(ChatRoom room, String user, Consumer<ChatRoom> onRoomClose){
         room.removeUser(user);
         if(room.checkIfRoomEmpty()){
             onRoomClose.accept(room);
             closeChatroom(room);
         }
     }

     public Boolean checkIfRoomExists(String name)
     {
         return chatRooms.contains(getRoomByName(name));
     }
}
