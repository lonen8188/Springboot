package org.zerock.boardboot.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.boardboot.entity.Memo;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass() {

        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    public void testInsertDummies() {

        IntStream.rangeClosed(1,100).forEach(i -> {
                        Memo memo = Memo.builder().memoText("Sample...."+i).build();
                        memoRepository.save(memo);
                });
    }

    @Test
    public void testSelect() {
        Long mno = 100L;
        Optional<Memo> result= memoRepository.findById(mno) ;
        System.out.println("=====================");

        if(result.isPresent()){
            Memo memo = result.get();
            System.out.println(memo);
        }
        //Hibernate: 쿼리문 먼저 생성
        //    select
        //        m1_0.mno,
        //        m1_0.memo_text 
        //    from
        //        tbl_memo m1_0 
        //    where
        //        m1_0.mno=?
        //=====================
        //Memo(mno=100, memoText=Sample....100)
    }

    @Test
    @Transactional
    public void testSelect2() {
    Long mno = 100L;
        Memo memo = memoRepository.getOne(mno) ;
        System.out.println("=====================");
        System.out.println(memo);

        //===================== 호출하는 순간 쿼리문 생성
        //Hibernate:
        //    select
        //        m1_0.mno,
        //        m1_0.memo_text
        //    from
        //        tbl_memo m1_0
        //    where
        //        m1_0.mno=?
        //Memo(mno=100, memoText=Sample....100)
    }


    @Test
    public void testUpdate() {

        Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();
        System.out.println(memoRepository.save(memo));

        //Hibernate:
        //    select
        //        m1_0.mno,
        //        m1_0.memo_text
        //    from
        //        tbl_memo m1_0
        //    where
        //        m1_0.mno=?
        //Hibernate:
        //    update
        //        tbl_memo
        //    set
        //        memo_text=?
        //    where
        //        mno=?
        //Memo(mno=100, memoText=Update Text)
    }

    @Test
    public void testDelete() {

        Long mno = 100L ;
        memoRepository.deleteById(mno);

        //Hibernate:
        //    select
        //        m1_0.mno,
        //        m1_0.memo_text
        //    from
        //        tbl_memo m1_0
        //    where
        //        m1_0.mno=?
        //Hibernate:
        //    delete
        //    from
        //        tbl_memo
        //    where
        //        mno=?
    }

    @Test
    public void testPageDefault() {
        // jpa에 내장된 페이징, 정렬 기법 활용

        Pageable pageable = PageRequest.of(0, 10);

        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println(result);


        //Hibernate:
        //    select
        //        m1_0.mno,
        //        m1_0.memo_text
        //    from
        //        tbl_memo m1_0
        //    limit
        //        ?, ?
        //Hibernate:
        //    select
        //        count(m1_0.mno)
        //    from
        //        tbl_memo m1_0
        //Page 1 of 10 containing org.zerock.boardboot.entity.Memo instances

        System.out.println("---------------------------------------");

        System.out.println("Total Pages: "+result.getTotalPages()); // 총 몇 페이지 

        System.out.println("Total Count: "+result.getTotalElements()); // 전체 개수

        System.out.println("Page Number: "+result.getNumber()); // 현재 페이지 번호

        System.out.println("Page Size: "+result.getSize()); // 페이지당 데이터 개수

        System.out.println("has next page?: "+result.hasNext());    // 다음 페이지 존재 여부

        System.out.println("first page?: "+result.isFirst());  //  시작페이지 여부

        //Page 1 of 10 containing org.zerock.boardboot.entity.Memo instances
        //---------------------------------------
        //Total Pages: 10
        //Total Count: 99
        //Page Number: 0
        //Page Size: 10
        //has next page?: true
        //first page?: true

        System.out.println("-----------------------------------");

        for(Memo memo : result.getContent()) {
            System.out.println(memo);
        //-----------------------------------
            //Memo(mno=1, memoText=Sample....1)
            //Memo(mno=2, memoText=Sample....2)
            //Memo(mno=3, memoText=Sample....3)
            //Memo(mno=4, memoText=Sample....4)
            //Memo(mno=5, memoText=Sample....5)
            //Memo(mno=6, memoText=Sample....6)
            //Memo(mno=7, memoText=Sample....7)
            //Memo(mno=8, memoText=Sample....8)
            //Memo(mno=9, memoText=Sample....9)
            //Memo(mno=10, memoText=Sample....10)
        }

    }

    @Test
    public void testSort() {

        Sort sort1 = Sort.by("mno").descending();

        Pageable pageable = PageRequest.of(0, 10, sort1);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });
        //Hibernate:
        //    select
        //        m1_0.mno,
        //        m1_0.memo_text
        //    from
        //        tbl_memo m1_0
        //    order by
        //        m1_0.mno desc
        //    limit
        //        ?, ?
        //Hibernate:
        //    select
        //        count(m1_0.mno)
        //    from
        //        tbl_memo m1_0
        //Memo(mno=99, memoText=Sample....99)
        //Memo(mno=98, memoText=Sample....98)
        //Memo(mno=97, memoText=Sample....97)
        //Memo(mno=96, memoText=Sample....96)
        //Memo(mno=95, memoText=Sample....95)
        //Memo(mno=94, memoText=Sample....94)
        //Memo(mno=93, memoText=Sample....93)
        //Memo(mno=92, memoText=Sample....92)
        //Memo(mno=91, memoText=Sample....91)
        //Memo(mno=90, memoText=Sample....90)
    }


    @Test
    public void testSortdual() {

        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2); // and 연결

        Pageable pageable = PageRequest.of(0, 10, sortAll);

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });
        //Hibernate:
        //    select
        //        m1_0.mno,
        //        m1_0.memo_text
        //    from
        //        tbl_memo m1_0
        //    order by
        //        m1_0.mno desc,
        //        m1_0.memo_text
        //    limit
        //        ?, ?
        //Hibernate:
        //    select
        //        count(m1_0.mno)
        //    from
        //        tbl_memo m1_0
        //Memo(mno=99, memoText=Sample....99)
        //Memo(mno=98, memoText=Sample....98)....
    }

    @Test
    public void testQueryMethods() {

        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);
        for(Memo memo : list) {
            System.out.println(memo);
        }
        //Hibernate:
        //    select
        //        m1_0.mno,
        //        m1_0.memo_text
        //    from
        //        tbl_memo m1_0
        //    where
        //        m1_0.mno between ? and ?
        //    order by
        //        m1_0.mno desc
        //Memo(mno=80, memoText=Sample....80)
        //Memo(mno=79, memoText=Sample....79)
        //Memo(mno=78, memoText=Sample....78)
        //Memo(mno=77, memoText=Sample....77)....

    }


    @Test
    public void testQueryMethodWithPagable() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10L,50L, pageable);

        result.get().forEach(memo -> System.out.println(memo));

        //Hibernate:
        //    select
        //        m1_0.mno,
        //        m1_0.memo_text
        //    from
        //        tbl_memo m1_0
        //    where
        //        m1_0.mno between ? and ?
        //    order by
        //        m1_0.mno desc
        //    limit
        //        ?, ?
        //Hibernate:
        //    select
        //        count(m1_0.mno)
        //    from
        //        tbl_memo m1_0
        //    where
        //        m1_0.mno between ? and ?
        //Memo(mno=50, memoText=Sample....50)
        //Memo(mno=49, memoText=Sample....49)
        //Memo(mno=48, memoText=Sample....48)
        //Memo(mno=47, memoText=Sample....47) .... 41
    }

    @Commit // 영구 저장용 (기본적으로 롤백 처리함)
    @Transactional  // select문과 delete문을 같이 사용하기에
    @Test
    public void testDeleteQueryMethods() {

        memoRepository.deleteMemoByMnoLessThan(10L);
        //Hibernate:
        //    select
        //        m1_0.mno,
        //        m1_0.memo_text
        //    from
        //        tbl_memo m1_0
        //    where
        //        m1_0.mno<?
        //Hibernate:
        //    delete
        //    from
        //        tbl_memo
        //    where
        //        mno=?
        //Hibernate:
        //    delete
        //    from
        //        tbl_memo
        //    where
        //        mno=?
        //Hibernate:
        //    delete
        //    from
        //        tbl_memo
        //    where
        //        mno=?
        //Hibernate:
        //    delete
        //    from
        //        tbl_memo
        //    where
        //        mno=?
        //Hibernate:
        //    delete
        //    from
        //        tbl_memo
        //    where
        //        mno=?
        //Hibernate:
        //    delete
        //    from
        //        tbl_memo
        //    where
        //        mno=?
        //Hibernate:
        //    delete
        //    from
        //        tbl_memo
        //    where
        //        mno=?
        //Hibernate:
        //    delete
        //    from
        //        tbl_memo
        //    where
        //        mno=?
        //Hibernate:
        //    delete
        //    from
        //        tbl_memo
        //    where
        //        mno=?
    }


}
