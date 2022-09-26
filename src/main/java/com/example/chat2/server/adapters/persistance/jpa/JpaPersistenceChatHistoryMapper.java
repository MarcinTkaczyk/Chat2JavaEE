package com.example.chat2.server.adapters.persistance.jpa;


import com.example.chat2.server.domain.ChatLog;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface JpaPersistenceChatHistoryMapper {


    ChatLogEntity toEntity(ChatLog chatLog);

    ChatLog toDomain(ChatLogEntity entity);

    @IterableMapping(elementTargetType = ChatLog.class)
    List<ChatLog> toDomain(List<ChatLogEntity> entities);

}
