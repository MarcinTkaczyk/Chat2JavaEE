package com.example.chat2.server.fileTransfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileDTO {

    private String fileName;
    private byte[] file;
}
