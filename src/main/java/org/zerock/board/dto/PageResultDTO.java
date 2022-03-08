package org.zerock.board.dto;


import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * JPA 에서는 Repository에서는 페이지 처리 결과를 Page<Entity>타입으로 반환합니다
 * 따라서 서비스 계층에서 이를 처리하기 위해서도 별도의 클래스를 만들어서 처리해야 한다.
 * 리턴을 받고 앞단에 뿌려줘야 하므로 Entity-> DTO 로 변환해줘야 합니다.
 */
@Data
public class PageResultDTO<DTO,EN> {

    private List<DTO>  dtoList;

    private int totalPage;

    private int page;

    private int size;

    private int start,end;

    private boolean prev,next;

    private List<Integer> pageList;

    /**
     * Entity 를 DTO로 바꿔 줘야 할때 쓰는 생성자  ------ 수정할때 삭제할때 써야 합니다...
     */
    public PageResultDTO(Page<EN> result, Function<EN,DTO> fn){
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage =result.getTotalPages();
        makePageList(result.getPageable());

    }
    private void makePageList(Pageable pageable){

        this.page=pageable.getPageNumber()+1;
        this.size=pageable.getPageSize();

        int tempEnd =(int)(Math.ceil(page/10.0))*10;

        start = tempEnd-9;

        prev = start >1 ;

        end = totalPage >tempEnd ? tempEnd: totalPage;

        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());
    }

}
