package org.zerock.boardboot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "m_member")
public class Mmember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mid;

    private String email;

    private String pw;

    private String nickname;

    //Hibernate:
    //    create table m_member (
    //        mid bigint not null auto_increment,
    //        email varchar(255),
    //        nickname varchar(255),
    //        pw varchar(255),
    //        primary key (mid)
    //    ) engine=InnoDB

}
