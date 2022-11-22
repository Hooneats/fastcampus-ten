package org.example.board.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.example.board.domain.ArticleComment;
import org.example.board.domain.QArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleCommentRepository extends
        JpaRepository<ArticleComment, Long>,
        QuerydslPredicateExecutor<ArticleComment>, // QuerydslPredicateExecutor 는 Article 안에있는 모든 필드에 기본검사 검색기능을 추가해준다.
        QuerydslBinderCustomizer<QArticleComment> // QuerydslPredicateExecutor 기본 검색은 where 절 동작 방식이기에 like 동작방식으로 바꾸기 위해서 customize 메서드를 만들어준다.
{

    @Override
    default void customize(QuerydslBindings bindings, QArticleComment root) {
        // QuerydslPredicateExecutor 에 의해 모든 필드가 열려있다. -> 선택적인 필드만 열리게끔 변경
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.content, root.createAt, root.createdBy);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}