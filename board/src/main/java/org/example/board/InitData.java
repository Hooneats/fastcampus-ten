package org.example.board;

import lombok.RequiredArgsConstructor;
import org.example.board.domain.Article;
import org.example.board.domain.ArticleComment;
import org.example.board.domain.UserAccount;
import org.example.board.repository.ArticleCommentRepository;
import org.example.board.repository.ArticleRepository;
import org.example.board.repository.UserAccountRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Profile({"local","test"})
@Component
@RequiredArgsConstructor
public class InitData {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        //Article
        final List<Article> articles = createArticlesDummyData();
        final List<Article> savedArticles = articleRepository.saveAll(articles);

        // Article_Comment
        final List<ArticleComment> articleComments = createArticleCommentsDummyData(savedArticles);
        articleCommentRepository.saveAll(articleComments);

        // UserAccount 테스트 계정
        final List<UserAccount> userAccounts = createUserAccountDummyData();
        userAccountRepository.saveAll(userAccounts);
    }

    private List<UserAccount> createUserAccountDummyData() {
        ArrayList<UserAccount> userAccounts = new ArrayList<>();
        IntStream.range(0, 2)
                .forEach(num ->
                        userAccounts.add(
                                UserAccount.of("hooneats" + num, "{noop}hooneats", "hooneats" + num + "@naver.com", "Hooneats" + num, "hooneatsMemo" + num)
                        )
                );
        return userAccounts;
    }

    private static List<Article> createArticlesDummyData() {
        final List<Article> articles = new ArrayList<>();
        IntStream.range(0, 30)
                .forEach(num ->
                        articles.add(
                                Article.of("new Article" + num, "new Content" + num, "#spring")
                        )
                );
        return articles;
    }

    private static List<ArticleComment> createArticleCommentsDummyData(List<Article> savedArticles) {
        final List<ArticleComment> articleComments = new ArrayList<>();
        IntStream.range(0, 25)
                .forEach(num ->
                        articleComments.add(ArticleComment.of(savedArticles.get(num), "new Comment_Content" + num))
                );
        return articleComments;
    }

}
