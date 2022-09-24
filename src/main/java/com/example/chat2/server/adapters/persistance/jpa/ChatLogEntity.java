package com.example.chat2.server.adapters.persistance.jpa;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@NoArgsConstructor
@Table(name = "chatlog")
public class ChatLogEntity {
    @Id
    @GeneratedValue
    @Column(name= "chatlog_id")
    private Long id;
    @ElementCollection
    @CollectionTable(name = "users")
    @Column(name = "chat_user")
    private Set<String> users;
    private String room;
    @ElementCollection
    @CollectionTable(name = "messages")
    private List<String> messages;
    private Date closeDate;
}
