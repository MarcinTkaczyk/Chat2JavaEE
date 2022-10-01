package com.example.chat2.server.fileTransfer;

public interface FileRepository {

    public String savefile(byte[] file, String fileName);

    public FileDTO getFileById(String id);
}
