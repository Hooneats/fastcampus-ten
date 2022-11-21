package org.example.board.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link org.example.board.domain.Article} entity
 *
 * TODO : 추후 17 버전부터 나온 불변객체 record 으로 변경
 */
@Data
public class ArticleDto implements Serializable {
    private final String title;
    private final String content;
    private final String hashtag;
    private final LocalDateTime createAt;
    private final String createdBy;
    private final LocalDateTime modifiedAt;
    private final String modifiedBy;
}