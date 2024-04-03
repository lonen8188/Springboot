package org.zerock.boardboot.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.boardboot.dto.GuestbookDTO;
import org.zerock.boardboot.dto.PageRequestDTO;
import org.zerock.boardboot.dto.PageResultDTO;
import org.zerock.boardboot.entity.Guestbook;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    private GuestbookService service;

    @Test
    public void testRegister() {

        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("user0")
                .build();

        System.out.println(service.register(guestbookDTO));

        //DTO------------------------
        //2024-04-03T15:49:08.974+09:00  INFO 2212 --- [boardBoot] [    Test worker] o.z.b.service.GuestbookServiceImpl       : GuestbookDTO(gno=null, title=Sample Title..., content=Sample Content..., writer=user0, regDate=null, modDate=null)
        //2024-04-03T15:49:08.982+09:00  INFO 2212 --- [boardBoot] [    Test worker] o.z.b.service.GuestbookServiceImpl       : Guestbook(gno=null, title=Sample Title..., content=Sample Content..., writer=user0)
        //Hibernate:
        //    insert
        //    into
        //        guestbook
        //        (content, moddate, regdate, title, writer)
        //    values
        //        (?, ?, ?, ?, ?)
        //301
    }

    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        }
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
        //    order by
        //        g1_0.gno desc
        //    limit
        //        ?, ?
        //Hibernate:
        //    select
        //        count(g1_0.gno)
        //    from
        //        guestbook g1_0
        //GuestbookDTO(gno=301, title=Sample Title..., content=Sample Content..., writer=user0, regDate=2024-04-03T15:49:09.055854, modDate=2024-04-03T15:49:09.055854)
        //GuestbookDTO(gno=300, title=Changed Title...., content=Changed Content..., writer=user0, regDate=2024-04-03T14:57:14.008920, modDate=2024-04-03T15:09:14.240332)
        //GuestbookDTO(gno=299, title=Title....299, content=Content...299, writer=user9, regDate=2024-04-03T14:57:14.004999, modDate=2024-04-03T14:57:14.004999)
        //GuestbookDTO(gno=298, title=Title....298, content=Content...298, writer=user8, regDate=2024-04-03T14:57:14.002727, modDate=2024-04-03T14:57:14.002727)
    }


    @Test
    public void testListOther(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        System.out.println("PREV: "+resultDTO.isPrev());
        System.out.println("NEXT: "+resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("-------------------------------------");
        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        }

        System.out.println("========================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));

//      Hibernate:
//        select
//        g1_0.gno,
//                g1_0.content,
//                g1_0.moddate,
//                g1_0.regdate,
//                g1_0.title,
//                g1_0.writer
//        from
//        guestbook g1_0
//        order by
//        g1_0.gno desc
//        limit
//                ?, ?
//        Hibernate:
//        select
//        count(g1_0.gno)
//        from
//        guestbook g1_0
//        PREV: false
//        NEXT: true
//        TOTAL: 31
//                -------------------------------------
//        ========================================
//        1
//        2
//        3
//        4
//        5
//        6
//        7
//        8
//        9
//        10
    }
//
//
    @Test
    public void testSearch(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc")   //검색 조건 t, c, w, tc, tcw ..
                .keyword("한글")  // 검색 키워드
                .build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        System.out.println("PREV: "+resultDTO.isPrev());
        System.out.println("NEXT: "+resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("-------------------------------------");
        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        }

        System.out.println("========================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
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
        //        g1_0.gno>?
        //        and (
        //            g1_0.title like ? escape '!'
        //            or g1_0.content like ? escape '!'
        //        )
        //    order by
        //        g1_0.gno desc
        //    limit
        //        ?, ?
        //PREV: false
        //NEXT: false
        //TOTAL: 0
        //-------------------------------------
    }




}
