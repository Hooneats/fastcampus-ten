package org.example.board.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "article_comment")
public class ArticleComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false) // 'optional=false' 필수, 또한 댓글은 지워져도 게시글은 지워지면 안되기에 cascade 는 기본값인 none 으로 사용
    private Article article; // 게시글 (ID)

    @Setter
    @Column(nullable = false, length = 500)
    private String content; // 본문

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createAt; // 생성일시

    @CreatedBy
    @Column(nullable = false, length = 100)
    private String createdBy; // 생성자

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt; // 수정일시

    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy; // 수정자

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
        if (!(o instanceof ArticleComment that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
