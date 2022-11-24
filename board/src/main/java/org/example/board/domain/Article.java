package org.example.board.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true) // callSuper 는 AuditingFields 까지 ToString 찍어내기 위해
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @JoinColumn(name = "userId")
    @ManyToOne(optional = false)
    private UserAccount userAccount; // 유저 정보 (ID)

    // @Column 은 생략이 가능하나 nullable false 등 설정을 위해
    @Setter @Column(nullable = false) private String title; // 제목
    @Setter @Column(nullable = false, length = 10000) private String content; // 본문

    @ToString.Exclude
    @JoinTable( // @ManyToMany 는 DB 에 드러나지 않는 테이블이 생기고 이 테이블을 직접 컨트롤할 수 없기에 실무에서는 @ManyToMany 보다는 실제 테이블을 하나 만들어 @OneToMany, @ManyToOne 를 사용한다.
            name = "article_hashtag",
            joinColumns = @JoinColumn(name = "articleId"), // article 의 id
            inverseJoinColumns = @JoinColumn(name = "hashtagId") // 매핑할 hashtag 의 id
    )
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}) // PERSIST 는 Insert , MERGE 는 update 이다.
        // -> Hashtag 는 name 이 unique 로 하나의 Hashtag 를 여러게시글에서 사용할 수 있기에 Delete 는 해주지 않았다.
    private Set<Hashtag> hashtags = new LinkedHashSet<>();


    // 실무에서는 양방향을 잘 안쓴다. 예를 들어 '게시글이 사라지면' 댓글이 삭제되는게 맞지만, 운영상 백업 목벅으로 댓글을 남기고 싶을 떄도 있기에
    @ToString.Exclude // 순환참조 문제로 ToString 을 끊는다면 OneToMany 에서 끊는다.
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();


    // JPA hibernate 는 기본 생성자가 필요하다.
    protected Article() {}

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
        if (!(o instanceof Article that)) return false; // 자바 버전 14 이후 패턴 매칭
        return this.getId() != null && this.getId().equals(that.getId()); // 영속화한 객체 즉, id 있는 것들만 비교대상이기에 'id != null &&' 추가
    }

    // 스프링 데이터 JPA 로 엔티티를 다룰때, 엔티티 데이터는 하이버네이트 구현체가 만든 프록시 객체를 이용하여 지연 로딩될 수 있다.
    // 따라서 엔티티를 조회할 때 필드에 직접 접근하면 'ex) id == null' 인 상황이 있을 수 있고, 이러면 올바른 비교를 하지 못하게 된다.
    // Equals & HashCode 에서도 getter 를 사용하면 지연로딩에 대한 문제를 해결할 수 있다.
    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }

}
