package org.zerock.boardboot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "movie") //연관 관계시 항상 주의
public class MovieImage  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;

    private String uuid;

    private String imgName;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY) //무조건 lazy로
    private Movie movie;
    //Hibernate:
    //    create table movie_image (
    //        inum bigint not null auto_increment,
    //        img_name varchar(255),
    //        path varchar(255),
    //        uuid varchar(255),
    //        movie_mno bigint,
    //        primary key (inum)
    //    ) engine=InnoDB
    //Hibernate:
    //    alter table if exists movie_image
    //       add constraint FKitwj3761d8j8ku189u4qrseih
    //       foreign key (movie_mno)
    //       references movie (mno)

}
