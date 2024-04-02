package org.zerock.boardboot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // 해당 클래스가 엔티티 역할 담당
@Table(name= "tbl_memo") // db 테이블 명 지정
@ToString // 객체를 문자열로 변경
@Getter     // 게터
@Builder    // 빌더 패턴 (@AllArgsConstructor, @NoArgsConstructor필수)
@AllArgsConstructor // 모든 매개값을 같는 생성자
@NoArgsConstructor  // 매개값이 없는 생성자
public class Memo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 스트래터지(방법,계략)
                                            // auto : JPA 구현체가 생성 방식을 결정
                                            // IDENTITY : MariaDB용 (auto increment)
                                            // SEQUENCE : Oracle용 (@SequenceGenerator와 같이 사용)
                                            // TABLE : 키생성 전용 테이블을 생성해 키 생성(@TableGenerator와 같이 사용)
    private Long mno ;
    
    @Column(length = 200, nullable = false) // 추가 열 설정 (글자길이 200 , 널허용 여부 
    private String memoText;

    //Hibernate:
    //    create table tbl_memo (
    //        mno bigint not null auto_increment,
    //        memo_text varchar(200) not null,
    //        primary key (mno)
    //    ) engine=InnoDB
    
}
