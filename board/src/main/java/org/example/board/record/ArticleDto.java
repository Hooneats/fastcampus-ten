package org.example.board.record;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * TODO : 불변객체 record
 */
public record ArticleDto(
        String title,
        String content,
        String hashtag,
        LocalDateTime createAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) implements Serializable {

}