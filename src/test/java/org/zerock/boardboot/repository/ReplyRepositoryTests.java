package org.zerock.boardboot.repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.boardboot.entity.Board;
import org.zerock.boardboot.entity.Reply;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply() {

        IntStream.rangeClosed(1, 300).forEach(i -> {
            //1부터 100까지의 임의의 번호
            long bno  = (long)(Math.random() * 100) + 1;

            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                    .text("Reply......." +i)
                    .board(board)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);

        });

    }

    @Test
    public void readReply1() {

        Optional<Reply> result = replyRepository.findById(1L);

        Reply reply = result.get();

        System.out.println(reply);
        System.out.println(reply.getBoard());

    }

    @Test
    public void testListByBoard() {

        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(
                Board.builder().bno(97L).build());

        replyList.forEach(reply -> System.out.println(reply));
        //Hibernate:
        //    select
        //        r1_0.rno,
        //        r1_0.board_bno,
        //        r1_0.moddate,
        //        r1_0.regdate,
        //        r1_0.replyer,
        //        r1_0.text
        //    from
        //        reply r1_0
        //    where
        //        r1_0.board_bno=?
        //    order by
        //        r1_0.rno
        //Hibernate:
        //    select
        //        b1_0.bno,
        //        b1_0.content,
        //        b1_0.moddate,
        //        b1_0.regdate,
        //        b1_0.title,
        //        b1_0.writer_email
        //    from
        //        board b1_0
        //    where
        //        b1_0.bno=?
        //Reply(rno=17, text=Reply.......17, replyer=guest)
        //Reply(rno=85, text=Reply.......85, replyer=guest)
        //Reply(rno=256, text=Reply.......256, replyer=guest)
    }


}
