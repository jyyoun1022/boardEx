package org.zerock.board.service;

import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;

public interface BoardService {

    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO,Object[]> getList(PageRequestDTO pageRequestDTO);

    BoardDTO get(Long bno);

    void removeWithReplies(Long bno);

    void modify(BoardDTO boardDTO);






   default Board dtoToEntity(BoardDTO dto){


       Member member= Member.builder().email(dto.getWriterEmail()).build();


       Board entity = Board.builder()
               .bno(dto.getBno())
               .title(dto.getTitle())
               .content(dto.getContent())
               .member(member)
               .build();
       return entity;
   }

   default BoardDTO entityToDto(Board board,Member member,Long replyCount){

       BoardDTO dto = BoardDTO.builder()
               .bno(board.getBno())
               .title(board.getTitle())
               .content(board.getContent())
               .writerEmail(member.getEmail())
               .writerName(member.getName())
               .replyCount(replyCount.intValue())
               .regDate(board.getRegDate())
               .modDate(board.getModDate())
               .build();

       return dto;
   }
}
