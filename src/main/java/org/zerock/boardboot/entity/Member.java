package org.zerock.boardboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "tbl_member")
public class Member extends BaseEntity {

    @Id // pk 선언
    private String email;

    private String password;

    private String name;

    //Hibernate:
    //    create table tbl_member (
    //        email varchar(255) not null,
    //        moddate datetime(6),
    //        regdate datetime(6),
    //        name varchar(255),
    //        password varchar(255),
    //        primary key (email)
    //    ) engine=InnoDB
}
