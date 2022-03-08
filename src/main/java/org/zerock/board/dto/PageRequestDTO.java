package org.zerock.board.dto;


import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 목록 페이지를 요청할 때 사용하는 데이터를 재사용하기 위해 만드는 클래스
 * Ex)  1. 페이지 번호   2. 페이지 내 목록의 개수     3. 검색 조건    등등
 * 화면에서 전달되는 목록 관련된 데이터에 대한 DTO, 화면에서 필요한 결과는 PageResultDTO로 생성
 */
@Builder
@AllArgsConstructor
@Getter @Setter @ToString
public class PageRequestDTO {

    private int page;
    private int size;
    private  String type;   //검색조건
    private String keyword; //검색 키워드

    /**
     *페이지 번호는 기본값을 가지는 것이 좋기때문에 생성자로 page=1,size=10으로 초기화 해줍니다.
     */
    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }

    /**
     * page-1을 하는 이유는 나중에 수정의 여지가 있기 때문에,
     * JPA를 이용하는 경우에는 페이지 번호가 0부터 시작한다는 점을 감안하여 page-1로 작성합니다.
     */
    public Pageable getPageable(Sort sort){

        return PageRequest.of(page-1,size ,sort);
    }


}
