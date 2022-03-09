package org.zerock.board.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.thymeleaf.util.StringUtils;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.QBoard;
import org.zerock.board.entity.QMember;
import org.zerock.board.entity.QReply;

import java.util.Arrays;
import java.util.List;


@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl(){
        super(Board.class);
    }


    @Override
    public Board search1() {

        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

//        JPQLQuery<Board> jpqlQuery = from(board);
//        jpqlQuery.leftJoin(member).on(board.member.eq(member));
//        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));
//
//        jpqlQuery.select(board,member.email,reply.count()).groupBy(board);

        List<Tuple> fetch = from(board).
                leftJoin(member).on(board.member.eq(member))
                .leftJoin(reply).on(reply.board.eq(board))
                .select(board, member.email, reply.count())
                .groupBy(board)
                .fetch();


        log.info("====================");
        for (Tuple tuple : fetch) {
            log.info(tuple);
        }
        log.info("====================");

//        List<Board> result = jpqlQuery.fetch();

        return null;
    }

    @Override
    public Board testSearch1() {

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board)).where(board.bno.eq(1L));

        List<Board> result = jpqlQuery.fetch();

        log.info("=================");
        log.info(jpqlQuery);
        log.info("=================");
        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;


        JPQLQuery<Tuple> tuple = from(board)
                .leftJoin(member).on(board.member.eq(member))
                .leftJoin(reply).on(reply.board.eq(board))
                .select(board, member, reply.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0L);

        booleanBuilder.and(expression);

        if(StringUtils.isEmpty(type)){
            String[] typeArr = type.split("");

            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t : typeArr) {

                switch (t) {
                    case "t":
                    case "T":
                    case "title":
                    case "Title":
                        conditionBuilder.or(board.title.contains(keyword));
                        break;
                    case "w":
                    case "W":
                    case "writer":
                    case "Writer":
                        conditionBuilder.or(member.email.contains(keyword));
                        break;
                    case "c":
                    case "C":
                    case "content":
                    case "Content":
                        conditionBuilder.or(board.content.contains(keyword));
                        break;
                }
            }
                booleanBuilder.and(conditionBuilder);
            }
            tuple.where(booleanBuilder);

        Sort sort = pageable.getSort();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending()? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Board.class,"board");


        });

        return null;
    }
}
