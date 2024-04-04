package org.zerock.boardboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.boardboot.entity.MovieImage;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {
}
