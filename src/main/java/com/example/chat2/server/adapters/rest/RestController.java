package com.example.chat2.server.adapters.rest;

import com.example.chat2.server.domain.EventsBus;
import com.example.chat2.server.domain.ServerEvent;
import com.example.chat2.server.domain.ServerEventType;
import com.example.chat2.server.fileTransfer.FileRepository;
import com.example.chat2.server.ports.in.FileIn;
import com.example.chat2.server.ports.model.FileIdMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import org.apache.commons.io.IOUtils;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.example.chat2.server.fileTransfer.FileDTO;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;

@Log

@Path("/filemessage")
@ApplicationScoped
@AllArgsConstructor(onConstructor_ = @Inject)
@RequiredArgsConstructor
public class RestController{

    FileIn fileIn;
    FileRepository fileRepository;
    EventsBus eventsBus;

    @PostConstruct
    void init(){
     log.info("------Rest controller initialized-----");
    }
    @PreDestroy
    void preDestroy(){
        log.info("------Destroying Rest Controller-----");
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response process(MultipartFormDataInput input) throws IOException {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        String fileName = uploadForm.get("fileName").get(0).getBodyAsString();
        String source = uploadForm.get("user").get(0).getBodyAsString();
        List<InputPart> inputParts = uploadForm.get("attachment");
        String id = null;

        for (InputPart inputPart : inputParts){
            try
            {

                //var header = inputPart.getHeaders();
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(inputStream);

                id = fileRepository.savefile(bytes, fileName);
                log.info("acquired file, id:" + id) ;
                var fileIdMessage = new FileIdMessage.FileIdMessageBuilder()
                        .fileName(fileName)
                        .id(id)
                        .build();
                fileIdMessage.setSource(source);

                eventsBus.publish(new ServerEvent.ServerEventBuilder()
                        .type(ServerEventType.FILE_RECEIVED)
                        .author(source)
                        .message(fileIdMessage)
                        .build());

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        return Response.status(201).entity(id).build();
    }

//    @GET
//    @Path("{id}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.MULTIPART_FORM_DATA)
//    public Response getFile(@PathParam("id") String id) {
//        var file = fileRepository.getFileById(id);
//        MultipartFormDataOutput output = new MultipartFormDataOutput();
//        output.addPart(file.getFileName(), MediaType.APPLICATION_JSON_TYPE);
//        output.addPart(file.getFile(), MediaType.APPLICATION_OCTET_STREAM_TYPE);
//
//        return Response.ok(output).
//        build();
//    }

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFile(@PathParam("id") String id) {
        var file = fileRepository.getFileById(id);
        return Response.ok(file.getFile(), MediaType.APPLICATION_OCTET_STREAM_TYPE).
                build();
    }

}
