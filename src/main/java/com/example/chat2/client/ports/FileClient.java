package com.example.chat2.client.ports;

import com.example.chat2.server.ports.model.FileMessage;
import java.io.File;

public interface FileClient {

    public void postFile(FileMessage fileMessage);
    FileMessage getFile(String id, String fileName, File downloadLocation);
}
