package com.example.chat2.server.ports.out;

import com.example.chat2.server.ports.model.AbstractChatMessage;

import javax.naming.NamingException;

public interface ServerOut {

    public void write(AbstractChatMessage message) throws NamingException;
}
