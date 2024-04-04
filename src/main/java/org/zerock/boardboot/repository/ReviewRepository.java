package org.zerock.boardboot.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.boardboot.entity.Mmember;
import org.zerock.boardboot.entity.Movie;
import org.zerock.boardboot.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @EntityGraph(attributePaths = {"mmember"}, type = EntityGraph.EntityGraphType.FETCH)
    // FETCH attributePaths에 명시한 속성은 EAGER 처리 나머진 LAZY 처리
    // LOAD attributePaths에 명시한 속성은 EAGER 처리 나머진 클래스에 명시되거나 기본 방식으로 처리
    List<Review> findByMovie(Movie movie);


    @Modifying
    @Query("delete from Review mr where mr.mmember = :mmember")
    void deleteByMember(Mmember mmember);
}
