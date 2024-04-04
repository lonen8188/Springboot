package org.zerock.boardboot.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.boardboot.entity.Movie;
import org.zerock.boardboot.entity.MovieImage;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class MovieRepositoryTests {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieImageRepository imageRepository;

    @Commit
    @Transactional
    @Test
    public void insertMovies() {

        IntStream.rangeClosed(1,100).forEach(i -> {

            Movie movie = Movie.builder().title("Movie...." +i).build();

            System.out.println("------------------------------------------");

            movieRepository.save(movie);

            int count = (int)(Math.random() * 5) + 1; //1,2,3,4


            for(int j = 0; j < count; j++){
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgName("test"+j+".jpg").build();

                imageRepository.save(movieImage);
            }


            System.out.println("===========================================");

        });
    }

    @Test
    public void testListPage(){

        PageRequest pageRequest = PageRequest.of(0,10, Sort.by(Sort.Direction.DESC, "mno"));

        Page<Object[]> result = movieRepository.getListPage(pageRequest);

        for (Object[] objects : result.getContent()) {
            System.out.println(Arrays.toString(objects));
        }
        //Hibernate:
        //    select
        //        m1_0.mno,
        //        m1_0.moddate,
        //        m1_0.regdate,
        //        m1_0.title,
        //        mi1_0.inum,
        //        mi1_0.img_name,
        //        mi1_0.movie_mno,
        //        mi1_0.path,
        //        mi1_0.uuid,
        //        avg(coalesce(r1_0.grade, 0)),
        //        count(r1_0.reviewnum)
        //    from
        //        movie m1_0
        //    left join
        //        movie_image mi1_0
        //            on mi1_0.movie_mno=m1_0.mno
        //    left join
        //        review r1_0
        //            on r1_0.movie_mno=m1_0.mno
        //    group by
        //        m1_0.mno
        //    order by
        //        m1_0.mno desc
        //    limit
        //        ?, ?
        //Hibernate:
        //    select
        //        count(m1_0.mno)
        //    from
        //        movie m1_0
        //    left join
        //        movie_image mi1_0
        //            on mi1_0.movie_mno=m1_0.mno
        //    left join
        //        review r1_0
        //            on r1_0.movie_mno=m1_0.mno
        //    group by
        //        m1_0.mno
        //[Movie(mno=100, title=Movie....100), MovieImage(inum=285, uuid=45bd6a7b-51e3-47ca-ab6b-b09f3346fecb, imgName=test0.jpg, path=null), 4.0, 12]
        //[Movie(mno=99, title=Movie....99), MovieImage(inum=283, uuid=035088b3-ee21-4d51-a3e5-faa5f3b4193b, imgName=test0.jpg, path=null), 2.0, 2]
        //[Movie(mno=98, title=Movie....98), MovieImage(inum=282, uuid=e7a33825-839e-4706-b52f-cd9e162344ee, imgName=test0.jpg, path=null), 3.0, 1]
        //[Movie(mno=97, title=Movie....97), MovieImage(inum=279, uuid=755e4842-3691-4d74-9af5-629ee5286ce1, imgName=test0.jpg, path=null), 4.0, 6]
    }

    @Test
    public void testGetMovieWithAll() {

        List<Object[]> result = movieRepository.getMovieWithAll(92L);

        System.out.println(result);

        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }

    }

}

