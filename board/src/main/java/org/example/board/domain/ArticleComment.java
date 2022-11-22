package org.example.board.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity(name = "article_comment")
public class ArticleComment extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false) // 'optional=false' 필수, 또한 댓글은 지워져도 게시글은 지워지면 안되기에 cascade 는 기본값인 none 으로 사용
    private Article article; // 게시글 (ID)

    @Setter
    @Column(nullable = false, length = 500)
    private String content; // 본문

    private ArticleComment(Article article, String content) {
        this.article = article;
        this.content = content;
    }

    public static ArticleComment of(Article article, String content) {
        return new ArticleComment(article, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ArticleComment that = (ArticleComment) o;
        if (!(o instanceof ArticleComment that)) return false; // 자바 버전 14 이후 패턴 매칭
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
