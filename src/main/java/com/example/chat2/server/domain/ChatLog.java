package com.example.chat2.server.domain;


import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.util.*;


@Getter
@Setter
public class ChatLog implements Serializable {
    private Set<String> users;
    private final String room;
    private List<String> messages;
    private Date closeDate;

    public ChatLog(String room){
        this.room = room;
        users = new HashSet<>();
        messages = new ArrayList<>();
    }

    public void log(String text){
        messages.add(text);
    }

    public void addUser(String user){
        users.add(user);
    }

    public void  setCloseDate(Date date){
        this.closeDate = date;
    }


}
