package org.zerock.board.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("보드 삽입")
    void insertBoard(){
        IntStream.rangeClosed(1,100).forEach(i->{

            Member member = Member.builder().email("user"+i+"@aaa.com").build();

            Board board = Board.builder()
                    .title("Title"+i)
                    .content("Content"+i)
                    .member(member)
                    .build();

            boardRepository.save(board);
        });
    }
    @Test
    @DisplayName("@ManyToOne으로 참조하고 있는 테스트 코드")
    @Transactional
    void testRead1(){

        Optional<Board> result = boardRepository.findById(99L);

        Board board = result.get();

        System.out.println(board);
        System.out.println("========================");
        System.out.println(board.getMember());
    }

    @Test
    @DisplayName("Member와Board 출력")
    void testBoardWithMember(){
        Object result = boardRepository.getBoardWithMember(100L);

        Object[] arr = (Object[]) result;

        System.out.println(Arrays.toString(arr));
    }

    @Test
    @DisplayName("목록,작성자,댓글개수 출력 ")
    void testWIthReplyCount(){

        Pageable pageable = PageRequest.of(0, 20, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        List<Object[]> content = result.getContent();

        content.stream().forEach(i -> System.out.println(Arrays.toString(i)));
        System.out.println();
        System.out.println(content.size());
    }
    @Test
    @DisplayName("조회화면에서 필요한 화면")
        void testRead2(){

        Object result = boardRepository.getBoardByBno(99L);

        Object[] arr = (Object[]) result;

        System.out.println(Arrays.toString(arr));
    }
    @Test
    @DisplayName("QUERYDSL 테스트")
    void testSearch1(){

        boardRepository.search1();
    }


    @Test
    @DisplayName("페이지 querydsl 테스트")
    void testSearch3(){

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.searchPage("t", "1", pageable);
    }

    /**
     * 여기서 왜 Title10을 검색하였는데 Title11 부터 쭉나오는지 의문..??(11-19),(21,31,41,....101) 왜?
     */
    @Test
    @DisplayName("페이지처리 테스트")
    void testSearchPage(){

        Pageable pageable = PageRequest.of(0,10,
                Sort.by("bno").descending()
                        .and(Sort.by("title").ascending()));

        Page<Object[]> result = boardRepository.searchPage("t", "Title10", pageable);

    }
    }

