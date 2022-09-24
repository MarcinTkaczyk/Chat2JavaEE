package com.example.chat2.server.ports.in;

import com.example.chat2.server.ports.model.FileMessage;

public interface FileIn {
    public void acceptFile(FileMessage fileMessage);
}
