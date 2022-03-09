package org.zerock.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Board;
import org.zerock.board.repository.search.SearchBoardRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> , SearchBoardRepository {

    /**
     *  틀정 게시물과 해당 작성자가 속한 게시물을 조회
     *  SQL = select * from board b left join member m on b.member_email=m.email where b.bno=100;
     */
    @Query("select b,m from Board b left join b.member m where b.bno = :bno ")
    Object getBoardWithMember(@Param("bno")Long bno);

    /**
     * 특정 게시물과 해당 게시물에 속한 댓글들을 조회
     * SQL = select * from board b left join reply r on b.bno=r.board_bno where b.bno=99;
     */
    @Query("select b,r from Board b left join Reply r on r.board=b where b.bno= :bno")
    List<Object[]> getBoardWithReply(@Param("bno")Long bno);

    @Query(value = "select b,m,count(r) " +
                    "from Board b " +
                    "left join b.member m " +
                    "left join Reply r on r.board=b " +
                    "group by b"

            ,countQuery = "select count(b) from Board b"
    )

    Page<Object[]>getBoardWithReplyCount(Pageable pageable);


    @Query("select b,m,count(r) " +
            "from Board b " +
            "left join b.member m " +
            "left join Reply r on r.board=b " +
            "where b.bno=:bno")
    Object getBoardByBno(@Param("bno")Long bno);


    }


