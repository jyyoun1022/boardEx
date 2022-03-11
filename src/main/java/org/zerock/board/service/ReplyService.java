package org.zerock.board.service;

import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

import java.util.List;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);

    List<ReplyDTO> getList(Long bno);

    void modify(ReplyDTO replyDTO);

    void remove(Long rno);

    default Reply dtoToEntity(ReplyDTO ReplyDTO){

        Board board = Board.builder().bno(ReplyDTO.getBno()).build();

        Reply entity = Reply.builder()
                .rno(ReplyDTO.getRno())
                .text(ReplyDTO.getText())
                .replier(ReplyDTO.getReplier())
                .board(board)
                .build();
        return  entity;
    }

    default ReplyDTO entityToDto(Reply reply){

        ReplyDTO dto =ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replier(reply.getReplier())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();

        return dto;
    }
}
