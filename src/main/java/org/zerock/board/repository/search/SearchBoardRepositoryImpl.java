package org.zerock.board.repository.search;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.QBoard;
import org.zerock.board.entity.QMember;
import org.zerock.board.entity.QReply;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.zerock.board.entity.QBoard.*;
import static org.zerock.board.entity.QMember.*;
import static org.zerock.board.entity.QReply.reply;


@Log4j2
@ToString
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }


    @Override
    public Board search1() {

        log.info("Search1.....");

        List<Tuple> result = from(board)
                .leftJoin(reply).on(reply.board.eq(board))
                .leftJoin(member).on(board.member.eq(member))
                .select(board, member.email, reply.count())
                .groupBy(board)
                .fetch();

        System.out.println("=======================");
        log.info(result);
        System.out.println("=======================");


        return null;
    }


    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        JPQLQuery<Board> jpqlQuery = from(board)
                .leftJoin(member).on(board.member.eq(member))
                .leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        BooleanExpression expression = board.bno.gt(0L);

        booleanBuilder.and(expression);


        if (type != null) {
            String[] typeArr = type.split("");

            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String s : typeArr) {

                switch (s) {
                    case "t":
                        conditionBuilder.or(board.title.containsIgnoreCase(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(board.content.containsIgnoreCase(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(board.member.email.containsIgnoreCase(keyword));
                        break;
                }
            }
                booleanBuilder.and(conditionBuilder);
            }

        List<Tuple> result = tuple.where(booleanBuilder)
                .groupBy(board)
                .fetch();

        log.info(result);


        return null;

        }


    }



