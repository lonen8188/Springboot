package org.zerock.boardboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.boardboot.entity.Mmember;

public interface MmemberRepository extends JpaRepository<Mmember, Long> {

}
