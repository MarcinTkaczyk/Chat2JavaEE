package com.example.chat2.server.domain;


import com.example.chat2.server.ports.in.FileIn;
import com.example.chat2.server.ports.model.FileMessage;
import lombok.extern.java.Log;

@Log
public class FileService implements FileIn {

    public void acceptFile(FileMessage fileMessage){
log.info("File received");
    }
}
