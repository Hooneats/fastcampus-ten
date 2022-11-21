package org.example.board;

import org.example.board.domain.Article;
import org.example.board.domain.ArticleComment;
import org.example.board.repository.ArticleCommentRepository;
import org.example.board.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.EventListener;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class InitTestData {

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ArticleCommentRepository articleCommentRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        //Article
        final List<Article> articles = new ArrayList<>();
        IntStream.range(0, 30)
                .forEach(num ->
                    articles.add(
                            Article.of("new Article" + num, "new Content" + num, "#spring")
                    )
        );
        List<Article> savedArticles = articleRepository.saveAll(articles);

        // Article_Comment
        final List<ArticleComment> articleComments = new ArrayList<>();
        IntStream.range(0, 25)
                .forEach(num ->
                        ArticleComment.of(savedArticles.get(num), "new Comment_Content" + num)
                );
        articleCommentRepository.saveAll(articleComments);
    }
}
