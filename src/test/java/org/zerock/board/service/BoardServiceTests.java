package org.zerock.board.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.repository.ReplyRepository;

import java.util.List;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;
    private ReplyRepository replyRepository;

    @Test
    @DisplayName("등록 테스트")
    void testRegister(){

        BoardDTO dto = BoardDTO.builder()
                        .title("테스트제목1")
                                .content("테스트내용1")
                                        .writerEmail("user55@aaa.com")
                                                .build();


        boardService.register(dto);

    }

    @Test
    @DisplayName("목록 갖고오기")
    void testList(){

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

       for (BoardDTO boardDTO : result.getDtoList()){
           System.out.println(boardDTO);
       }

    }
    @Test
    @DisplayName("상세조회")
    void testGet(){

        Long bno=100L;

        BoardDTO boardDTO = boardService.get(bno);

        System.out.println(boardDTO);
    }
    @Test
    @DisplayName("삭제테스트")
    void testRemove(){

        Long bno =1L;

        boardService.removeWithReplies(bno);
    }

    @Test
    @DisplayName("수정테스트")
    void testModify(){

        BoardDTO boardDTO = BoardDTO.builder()
                .title("수정테스트!!")
                .content("Modify Test")
                .bno(2L)
                .build();


        boardService.modify(boardDTO);

    }
}
