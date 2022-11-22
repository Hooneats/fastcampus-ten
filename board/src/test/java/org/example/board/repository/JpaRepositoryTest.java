package org.example.board.repository;

import org.example.board.InitData;
import org.example.board.config.JpaConfig;
import org.example.board.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test") // test 모듈에 application-test.properties
@DisplayName("JPA 연결 테스트")
@Import({JpaConfig.class, InitData.class}) // auditing 기능, dummy data
@DataJpaTest // @DataJpaTest 안에 @Transactional 있다. 또한 자동으로 in-memory db 를 사용한다. 스프링 부트가 자동으로 인메모리를 만들지 않고 설정파일기준으로 만들기 위해 @AutoConfigureTestDatabase 사용
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    // JUNIT 5 와 최신의 스프링부트는 테스트에서도 생성자 주입 가능
    //  --> @DataJpaTest 안에 @ExtendWith(SpringExtension.class) 안에 @Autowired 관련 로직 있다.
    JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("Select test")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        //Given

        //When
        final List<Article> articles = articleRepository.findAll();

        //Then
        assertThat(articles)
                .isNotNull()
                .hasSize(30);
    }

    @DisplayName("Insert test")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        //Given
        final long previousCount = articleRepository.count();
        final Article article = Article.of("new Article", "new Content", "#spring");

        //When
        final Article savedArticle = articleRepository.save(article);

        //Then
        assertThat(articleRepository.count())
                .isEqualTo(previousCount + 1);
    }

    @DisplayName("update test")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {
        //Given
        final Article article = articleRepository.findById(1L).orElseThrow();
        final String updatedHashtag = "#SpringBoot";
        article.setHashtag(updatedHashtag);

        //When
        final Article savedArticle = articleRepository.saveAndFlush(article); // 테스트는 @Transactional 로 인해 rollback 이 되기에
//        articleRepository.flush();

        //Then
        assertThat(savedArticle)
                .hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @DisplayName("delete test")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        //Given
        final Article article = articleRepository.findById(1L).orElseThrow();
        final long previousArticleCount = articleRepository.count();
        final long previousArticleCommentCount = articleCommentRepository.count();
        final int deletedCommentsSize = article.getArticleComments().size(); // caseCade 에 의해

        //When
        articleRepository.delete(article);

        //Then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentsSize );
    }
}