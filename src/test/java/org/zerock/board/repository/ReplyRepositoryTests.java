package org.zerock.board.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    @DisplayName("댓글 생성 ")
    void insertReply(){

        IntStream.rangeClosed(1,300).forEach(i->{
            long bno = (long) (Math.random() * 100) + 1;

            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                    .text("Reply.."+i)
                    .replyer("guest")
                    .board(board)
                    .build();

            replyRepository.save(reply);
        });
    }

    @Test
    @DisplayName("@ManyToOne 발현 보기")
    void readReplyTest1(){

        Optional<Reply> result = replyRepository.findById(100L);

        Reply reply = result.get();

        System.out.println(reply);
        System.out.println("==================");
        System.out.println(reply.getBoard());
    }
}
