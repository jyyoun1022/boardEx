package org.zerock.board.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.entity.Board;

import java.util.List;

@SpringBootTest
public class ReplyServiceTests {

    @Autowired
    private ReplyService replyService;

    @Test
    @DisplayName("댓글서비스")
    void replyServiceTest(){

        Long bno= 75L;

        List<ReplyDTO> result = replyService.getList(bno);

        result.forEach(replyDTO -> System.out.println(replyDTO));

    }
}
