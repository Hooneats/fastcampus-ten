package org.example.board;

import lombok.RequiredArgsConstructor;
import org.example.board.domain.Article;
import org.example.board.domain.ArticleComment;
import org.example.board.repository.ArticleCommentRepository;
import org.example.board.repository.ArticleRepository;
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

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        //Article
        final List<Article> articles = createArticlesDummyData();
        final List<Article> savedArticles = articleRepository.saveAll(articles);

        // Article_Comment
        final List<ArticleComment> articleComments = createArticleCommentsDummyData(savedArticles);
        articleCommentRepository.saveAll(articleComments);
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
