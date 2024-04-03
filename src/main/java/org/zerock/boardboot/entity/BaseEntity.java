package org.zerock.boardboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDateTime;

@MappedSuperclass // 엔티티 이지만 클래스로 생성되지 않음
@EntityListeners(value = { AuditingEntityListener.class }) // 엔티티 객체가 생성/변경을 감지하는 역할
// 감시동작에 필요한 필수 요소 main 메서드에 추가(@EnableJpaAuditing)
@Getter
abstract class BaseEntity {  // abstract 추상클래스로 지정

    @CreatedDate // 엔티티 생성시간처리
    @Column(name = "regdate", updatable = false) // 엔티티 객체를 데이터베이스에 반영할 때 변경 안함.
    private LocalDateTime regDate;

    @LastModifiedDate // 최종 수정시간을 자동으로 처리
    @Column(name ="moddate" )
    private LocalDateTime modDate;

}
