package com.example.chat2.client.ports;


import com.example.chat2.server.ports.model.FileMessage;

public interface FileClient {

    public void postFile(FileMessage fileMessage);
    public FileMessage getFile(String id);
}
