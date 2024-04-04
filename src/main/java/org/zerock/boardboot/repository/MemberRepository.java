package org.zerock.boardboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.boardboot.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
}
