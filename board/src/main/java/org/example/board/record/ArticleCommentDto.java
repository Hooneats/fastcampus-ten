package org.example.board.record;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * TODO : 불변객체 record
 */
public record ArticleCommentDto(
        ArticleDto article,
        String content,
        LocalDateTime createAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) implements Serializable {

}