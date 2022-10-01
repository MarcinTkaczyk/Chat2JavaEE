package com.example.chat2.server.fileTransfer;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class InMemoryFileRepository implements FileRepository {

    Map<String, FileDTO> fileMap = new HashMap<>();

    @Override
    public String savefile(byte[] file, String fileName) {
        var id = UUID.randomUUID().toString();
        var fileObject = new FileDTO(fileName, file);
        fileMap.put(id, fileObject);
        return  id;
    }

    @Override
    public FileDTO getFileById(String id) {
        return fileMap.get(id);
    }
}
