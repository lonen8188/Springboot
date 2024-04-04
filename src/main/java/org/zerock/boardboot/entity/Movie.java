package org.zerock.boardboot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Movie extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    private String title;
    //Hibernate:
    //    create table movie (
    //        mno bigint not null auto_increment,
    //        moddate datetime(6),
    //        regdate datetime(6),
    //        title varchar(255),
    //        primary key (mno)
    //    ) engine=InnoDB
}
