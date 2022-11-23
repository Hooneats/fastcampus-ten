package org.example.mycode.record;

import java.io.Serializable;

/**
 * TODO : 불변객체 record
 *  --> 참고로 설정 - 인텐션 - multiple lines 기능으로 한줄 또는 여러줄로 바꿀 수 있다.
 */
public record ArticleUpdateDto(
        String title,
        String content,
        String hashtag
) implements Serializable { // TODO Serializable 은 JackSon 이라는 직렬화 도구를 이용하기에 사실상 필요하지 않다.

    // record 는 이미 생성자 존재함
    public static ArticleUpdateDto of(String title, String content, String hashtag) {
        return new ArticleUpdateDto(title, content, hashtag);
    }
}