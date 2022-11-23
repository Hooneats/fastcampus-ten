package org.example.mycode.dto;

import lombok.Data;
import org.example.mycode.domain.Article;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link Article} entity
 *
 * TODO : 추후 17 버전부터 나온 불변객체 record 으로 변경
 */
@Data
public class ArticleDto_Before implements Serializable {
    private final String title;
    private final String content;
    private final String hashtag;
    private final LocalDateTime createAt;
    private final String createdBy;
    private final LocalDateTime modifiedAt;
    private final String modifiedBy;
}