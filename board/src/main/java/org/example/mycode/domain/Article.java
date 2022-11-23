package org.example.mycode.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA hibernate 는 기본 생성자가 필요하다.
@Getter
@ToString(callSuper = true) // callSuper 는 AuditingFields 까지 ToString 찍어내기 위해
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity(name = "article")
public class Article extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @JoinColumn(name = "userId")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserAccount userAccount; // 유저 정보 (ID)

    @Setter
    @Column(nullable = false) // @Column 은 생략이 가능하나 nullable false 등 설정을 위해
    private String title; // 제목

    @Setter
    @Column(nullable = false, length = 10000)
    private String content; // 본문

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "article_hashtag",
            joinColumns = @JoinColumn(name = "articleId"), // article 의 id
            inverseJoinColumns = @JoinColumn(name = "hashtagId") // 매핑할 hashtag 의 id
    )
    @ToString.Exclude
    private Set<Hashtag> hashtags = new LinkedHashSet<>();

    // 양방향
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL) // 실무에서는 양방향을 잘 안쓴다. 예를 들어 '게시글이 사라지면' 댓글이 삭제되는게 맞지만, 운영상 백업 목벅으로 댓글을 남기고 싶을 떄도 있기에
    @OrderBy("createdAt DESC")
    @ToString.Exclude // 순환참조 문제로 ToString 을 끊는다면 OneToMany 에서 끊는다.
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>(); // 중복 허용X

    // Auditing 기능 따로 클래스로 뺐을 때
    // 치환되는 방식 @Embedded
    // 상속 방식 @MappedSuperClass

    private Article(UserAccount userAccount, String title, String content) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
    }

    // 팩토리 메서드 패턴 (보통 여러 매개변수로 할 경우 메서드 이름으로 of 를 사용)
    public static Article of(UserAccount userAccount, String title, String content) {
        return new Article(userAccount, title, content);
    }

    public void addHashtag(Hashtag hashtag) {
        this.getHashtags().add(hashtag);
    }

    public void addHashtags(Collection<Hashtag> hashtags) {
        this.getHashtags().addAll(hashtags);
    }

    public void clearHashtags() {
        this.getHashtags().clear();
    }

    /**
     * JPA 엔터티에 @EqualsAndHashCode를 사용하는 것은 권장되지 않습니다.
     * 심각한 성능 및 메모리 소비 문제를 일으킬 수 있기에, 롬복 사용이아닌 코드 사용
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Article article = (Article) o;
        if (!(o instanceof Article article)) return false; // 자바 버전 14 이후 패턴 매칭
        return id != null && id.equals(article.id); // 영속화한 객체 즉, id 있는 것들만 비교대상이기에 'id != null &&' 추가
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
