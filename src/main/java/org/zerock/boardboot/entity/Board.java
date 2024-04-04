package org.zerock.boardboot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")
public class Board extends BaseEntity {

    @Id // pk 선언 및 자동번호 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩 fk 설정(나는 많고 상대편은 1개이다.)
    private Member writer; //부모

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }
    //Hibernate:
    //    create table board (
    //        bno bigint not null auto_increment,
    //        moddate datetime(6),
    //        regdate datetime(6),
    //        content varchar(255),
    //        title varchar(255),
    //        writer_email varchar(255),
    //        primary key (bno)
    //    ) engine=InnoDB
    //Hibernate:
    //    alter table if exists board
    //       add constraint FKsv8b40a4mo0vwhuhr8ryedhoa
    //       foreign key (writer_email)
    //       references tbl_member (email)
}

