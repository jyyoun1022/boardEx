package org.zerock.board.dto;


import lombok.*;

import java.time.LocalDateTime;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardDTO {

    private Long bno;

    private String title,content;

    private String writerEmail,writerName;

    private LocalDateTime regDate,modDate;

    private int replyCount;


}
