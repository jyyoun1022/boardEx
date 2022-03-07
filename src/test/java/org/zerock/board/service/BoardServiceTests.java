package org.zerock.board.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.BoardDTO;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

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
}
