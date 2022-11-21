package org.example.board.repository;

import org.example.board.InitTestData;
import org.example.board.config.JpaConfig;
import org.example.board.domain.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA 연결 테스트")
@Import({JpaConfig.class, InitTestData.class})
// TODO : 참고하자 - https://charliezip.tistory.com/21
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
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
        long previousCount = articleRepository.count();
        final Article article = Article.of("new Article", "new Content", "#spring");

        //When
        final Article savedArticle = articleRepository.save(article);

        //Then
        assertThat(articleRepository.count())
                .isEqualTo(previousCount + 1);
    }
}