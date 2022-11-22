package org.example.board.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.example.board.domain.Article;
import org.example.board.domain.QArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>,
        QuerydslBinderCustomizer<QArticle>
{

    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        // QuerydslPredicateExecutor 에 의해 모든 필드가 열려있다. -> 선택적인 필드만 열리게끔 변경
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.title, root.content, root.hashtags, root.createdAt, root.createdBy);
//        bindings.bind(root.title).first(StringExpression::likeIgnoreCase); // 대소문자 구분없이 like '${v} -> % 넣는것을 해줘야함
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // 대소문자 구분없이 like '%${v}%'
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // 대소문자 구분없이 like '%${v}%'
        bindings.bind(root.hashtags.any().hashtagName).first(StringExpression::containsIgnoreCase); // 대소문자 구분없이 like '%${v}%'
        bindings.bind(root.createdAt).first(DateTimeExpression::eq); // 대소문자 구분없이 like '%${v}%'
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase); // 대소문자 구분없이 like '%${v}%'
    }
}