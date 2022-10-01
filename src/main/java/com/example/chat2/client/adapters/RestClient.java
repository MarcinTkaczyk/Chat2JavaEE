package com.example.chat2.client.adapters;

import com.example.chat2.client.ports.FileClient;
import com.example.chat2.server.ports.model.FileMessage;
import lombok.extern.java.Log;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Log
public class RestClient implements FileClient {
    static String SERVER_URL = "http://localhost:8080/Chat2-1.0-SNAPSHOT/api/filemessage";

    @Override
    public void postFile(FileMessage fileMessage){
        var user = fileMessage.getSource();
        var fileName = fileMessage.getName();
        String id = null;

        try{

            var multipartEntity = MultipartEntityBuilder.create()
                    .addTextBody("user", user != null ? user : "")
                    .addTextBody("fileName", fileName != null ? fileName : "")
                   .addBinaryBody("attachment", fileMessage.getFile())
                    .build();

            ClassicHttpResponse response = (ClassicHttpResponse) Request.post(SERVER_URL).body(multipartEntity).execute().returnResponse();
            var entity = response.getEntity();
            id = EntityUtils.toString(entity);
//            log.info(id);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
  }


//    @Override
//    public FileMessage getFile(String id) {
//        var requestURL = SERVER_URL + "/" + id;
//        try {
//            ClassicHttpResponse response = (ClassicHttpResponse) Request.get(requestURL).execute().returnResponse();
//            var entity = response.getEntity();
//            var multipart = new MimeMultipart(new ByteArrayDataSource(response.getEntity().getContent(), "multipart/form-data"));
//            var count = multipart.getCount();
//
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (MessagingException e) {
//            throw new RuntimeException(e);
//        }
//        log.info("Downloading");
//
//
//        return null;
//    }

    @Override
    public FileMessage getFile(String id, String fileName, File downloadLocation) {
        var requestURL = SERVER_URL + "/" + id;
        String destination = downloadLocation.getAbsolutePath()+File.separator+fileName;

        try (FileOutputStream fileOutputStream = new FileOutputStream(destination, false)){
            var response =  Request.get(requestURL).execute();
            var bytes = response.returnContent().asBytes();
            fileOutputStream.write(bytes);
            log.info("Downloaded file : " + destination);

        } catch (IOException e) {
            throw new RuntimeException(e);


        }



        return null;
    }
}
