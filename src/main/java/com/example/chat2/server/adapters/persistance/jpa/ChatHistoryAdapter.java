package com.example.chat2.server.adapters.persistance.jpa;

import com.example.chat2.server.domain.ChatLog;
import com.example.chat2.server.ports.out.ChatRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ChatHistoryAdapter implements ChatRepository {

   private JpaPersistenceChatHistoryMapper chatHistoryMapper;
   EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PostgresDS");

   @Inject
    public ChatHistoryAdapter(JpaPersistenceChatHistoryMapper chatHistoryMapper) {
        this.chatHistoryMapper = chatHistoryMapper;
    }

    @Override
   public ChatLog save(ChatLog chatLog) {
       var entity = chatHistoryMapper.toEntity(chatLog);
         Utils.runInTransaction(entityManagerFactory, entityManager -> {
            entityManager.persist(entity);
         });

      return chatLog;
   }

   @Override
   public List<ChatLog> getByUser(String user) {

         EntityManager entityManager = entityManagerFactory.createEntityManager();
         var query = "from com.example.chat2.server.adapters.persistance.jpa.ChatLogEntity c join fetch c.users u where u.user = :user" ;
         var result = entityManager.createQuery(query, ChatLogEntity.class)
                 .setParameter("user", user)
                 .getResultList();
         var data = chatHistoryMapper.toDomain(result);

      return data;
   }

    @Override
    public List<ChatLog> getByUserAndRoom(String user, String room) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        var query = "from com.example.chat2.server.adapters.persistance.jpa.ChatLogEntity c where :user member of c.users and c.room = :room" ;

        var result = entityManager.createQuery(query, ChatLogEntity.class)
                .setParameter("user", user)
                .setParameter("room", room)
                .getResultList();
        var data = chatHistoryMapper.toDomain(result);

        return data;
    }
}
