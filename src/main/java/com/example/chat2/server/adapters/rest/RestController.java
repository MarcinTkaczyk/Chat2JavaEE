package com.example.chat2.server.adapters.rest;

import com.example.chat2.server.domain.ChatStarter;
import com.example.chat2.server.ports.in.FileIn;
import com.example.chat2.server.ports.model.FileMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Log
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/filemessage")
@ApplicationScoped
@AllArgsConstructor(onConstructor_ = @Inject)
@RequiredArgsConstructor
public class RestController{

    FileIn fileIn;

    @PostConstruct
    void init(
  //          @Observes @Initialized( ApplicationScoped.class ) Object init
    ){
     log.info("------Rest controller initialized-----");
    }
    @PreDestroy
    void preDestroy(){
        log.info("------Destroying Rest COntroller-----");
    }

    @POST
    public Response process(FileMessage fileMessage, @Context UriInfo uriInfo){
        fileIn.acceptFile(fileMessage);
        var locationUri = uriInfo.getAbsolutePathBuilder().path("id").build();
        return Response.created(locationUri).build();
    }

        @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }

    @POST()
    @Path("string")

    public Response processstring(String string, @Context UriInfo uriInfo){
        System.out.println(string);
        var locationUri = uriInfo.getAbsolutePathBuilder().path("id").build();
        return Response.created(locationUri).build();
    }

}
