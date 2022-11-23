package org.example.mycode.dto;

import lombok.Data;
import org.example.mycode.domain.ArticleComment;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link ArticleComment} entity
 *
 * TODO : 추후 17 버전부터 나온 불변객체 record 으로 변경
 */
@Data
public class ArticleCommentDto_Before implements Serializable {
    private final ArticleDto_Before article;
    private final String content;
    private final LocalDateTime createAt;
    private final String createdBy;
    private final LocalDateTime modifiedAt;
    private final String modifiedBy;
}