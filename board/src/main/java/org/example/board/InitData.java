package org.example.board;

import lombok.RequiredArgsConstructor;
import org.example.board.domain.Article;
import org.example.board.domain.ArticleComment;
import org.example.board.domain.Hashtag;
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
import java.util.Random;
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
        // UserAccount 테스트 계정
        final List<UserAccount> userAccounts = createUserAccountDummyData();
        final List<UserAccount> savedUserAccounts = userAccountRepository.saveAll(userAccounts);

        //Article
        final List<Article> articles = createArticlesDummyData(savedUserAccounts);
        final List<Article> savedArticles = articleRepository.saveAll(articles);

        // Article_Comment
        final List<ArticleComment> articleComments = createArticleCommentsDummyData(savedArticles, savedUserAccounts);
        articleCommentRepository.saveAll(articleComments);
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

    private  List<Article> createArticlesDummyData(List<UserAccount> savedUserAccounts) {
        final List<Article> articles = new ArrayList<>();
        List<Hashtag> hashtags = createHashtagDummyData();
        Random random = new Random();
        IntStream.range(0, 30)
                .forEach(num -> {
                    Article article = Article.of(savedUserAccounts.get(0), "new Article" + num, "new Content" + num);
                    int randomIndex = random.nextInt(3);
                    article.addHashtag(hashtags.get(randomIndex));
                    articles.add(article);
                });
        return articles;
    }

    private List<Hashtag> createHashtagDummyData() {
        List<Hashtag> hashtags = List.of(Hashtag.of("#java"), Hashtag.of("spring"), Hashtag.of("boot"), Hashtag.of("jpa"));
        return hashtags;
    }

    private  List<ArticleComment> createArticleCommentsDummyData(List<Article> savedArticles, List<UserAccount> savedUserAccounts) {
        final List<ArticleComment> articleComments = new ArrayList<>();
        IntStream.range(0, 25)
                .forEach(num ->
                        articleComments.add(ArticleComment.of(savedArticles.get(num), savedUserAccounts.get(0),"new Comment_Content" + num))
                );
        return articleComments;
    }

}
