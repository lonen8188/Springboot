package org.zerock.boardboot.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.boardboot.entity.Guestbook;
import org.zerock.boardboot.entity.QGuestbook;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies(){
   IntStream.rangeClosed(1,300).forEach(i -> {

        Guestbook guestbook = Guestbook.builder()
                .title("Title...." + i)
                .content("Content..." +i)
                .writer("user" + (i % 10))
                .build();
        System.out.println(guestbookRepository.save(guestbook));
    });
}

    @Test
    public void updateTest() {

        Optional<Guestbook> result = guestbookRepository.findById(300L); //존재하는 번호로 테스트

        if(result.isPresent()){

            Guestbook guestbook = result.get();

            guestbook.changeTitle("Changed Title....");
            guestbook.changeContent("Changed Content...");

            guestbookRepository.save(guestbook);
        }
    }

    @Test
    public void testQuery1() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook; //1. 가장먼저 동적으로 처리하기 위해서 Q도메인 클래스를 얻는다.

        String keyword = "1";  // 키워드가 1일 값을 찾을 것이다.

        BooleanBuilder builder = new BooleanBuilder();  //2 //where에 들어갈 조건을 넣어주는 컨테이너

        BooleanExpression expression = qGuestbook.title.contains(keyword); //3 import com.querydsl.core.types.dsl.BooleanExpression;
        // 제목에 1인 값을 찾는 where문 생성

        builder.and(expression); //4 and나 or와 같은 키워드를 결합

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable); //5 JPA의 findAll을 사용

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
        //Hibernate:
        //    select
        //        g1_0.gno,
        //        g1_0.content,
        //        g1_0.moddate,
        //        g1_0.regdate,
        //        g1_0.title,
        //        g1_0.writer
        //    from
        //        guestbook g1_0
        //    where
        //        g1_0.title like ? escape '!'
        //    order by
        //        g1_0.gno desc
        //    limit
        //        ?, ?
        //Hibernate:
        //    select
        //        count(g1_0.gno)
        //    from
        //        guestbook g1_0
        //    where
        //        g1_0.title like ? escape '!'
        //Guestbook(gno=291, title=Title....291, content=Content...291, writer=user1)
        //Guestbook(gno=281, title=Title....281, content=Content...281, writer=user1)
        //Guestbook(gno=271, title=Title....271, content=Content...271, writer=user1)
    }

    @Test
    public void testQuery2() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle =  qGuestbook.title.contains(keyword);

        BooleanExpression exContent =  qGuestbook.content.contains(keyword);

        BooleanExpression exAll = exTitle.or(exContent); // 1----------------

        builder.and(exAll); //2-----

        builder.and(qGuestbook.gno.gt(0L)); // 3-----------

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
        //Hibernate:
        //    select
        //        g1_0.gno,
        //        g1_0.content,
        //        g1_0.moddate,
        //        g1_0.regdate,
        //        g1_0.title,
        //        g1_0.writer
        //    from
        //        guestbook g1_0
        //    where
        //        (
        //            g1_0.title like ? escape '!'
        //            or g1_0.content like ? escape '!'
        //        )
        //        and g1_0.gno>?
        //    order by
        //        g1_0.gno desc
        //    limit
        //        ?, ?
        //Hibernate:
        //    select
        //        count(g1_0.gno)
        //    from
        //        guestbook g1_0
        //    where
        //        (
        //            g1_0.title like ? escape '!'
        //            or g1_0.content like ? escape '!'
        //        )
        //        and g1_0.gno>?
        //Guestbook(gno=291, title=Title....291, content=Content...291, writer=user1)
        //Guestbook(gno=281, title=Title....281, content=Content...281, writer=user1)
        //Guestbook(gno=271, title=Title....271, content=Content...271, writer=user1)
        //Guestbook(gno=261, title=Title....261, content=Content...261, writer=user1)
    }
}
