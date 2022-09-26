package com.example.chat2.client.adapters;

import com.example.chat2.client.ports.FileClient;
import com.example.chat2.server.ports.model.FileMessage;
import lombok.extern.java.Log;

@Log
public class RestClient implements FileClient {
    static String SERVER_URL = "http://localhost:8080/rest/filemessage";

    @Override
    public void postFile(FileMessage fileMessage){
//        ResteasyClient client = (ResteasyClient) ClientBuilder.newClient();
//        ResteasyWebTarget target = client.target(SERVER_URL);
//        Invocation.Builder request = target.request();
//        Response response = null;
//        try {
//            response = target.request().post(Entity.json(fileMessage));
//            log.info(String.valueOf(response.getStatusInfo()));
//        }
//        finally {
//            response.close();
//            client.close();
//        }
  }


    @Override
    public FileMessage getFile(String id) {return null;
    }
}
