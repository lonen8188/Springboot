package org.zerock.boardboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.zerock.boardboot.entity.Guestbook;

public interface GuestbookRepository 
        extends JpaRepository<Guestbook, Long>, QuerydslPredicateExecutor<Guestbook> {
    //, QuerydslPredicateExecutor<Guestbook> 쿼리Dsl 사용(인터페이스는 다중 상속 됨

}
