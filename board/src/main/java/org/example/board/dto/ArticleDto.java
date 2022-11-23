package org.example.board.dto;

import org.example.board.domain.Article;
import org.example.board.domain.UserAccount;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO : 불변객체 record
 *  --> 참고로 설정 - 인텐션 - multiple lines 기능으로 한줄 또는 여러줄로 바꿀 수 있다.
 *  특징 :
 *      - final 클래스이므로 다른 클래스가 상속할 수 없습니다.
 *      - 다른 클래스를 상속할 수도 없습니다.
 *      - 자동생성 accessor 함수는 인스턴스 멤버 변수의 이름과 동일합니다.
 *      - Record 클래스의 접근제어자는 public, package-private 만 가능합니다.
 *      - Record 생성자의 접근제어자는 클래스의 접근제어자보다 제한된 수준이어서는 안됩니다.
 *          ㄴ> (컴파일 오류 예시) default 접근제어자인 package-private은 public 보다 제한된 수준이므로 불가
 *
 * // TODO Serializable 은 JackSon 이라는 직렬화 도구를 이용하기에 사실상 필요하지 않다.
 */
public record ArticleDto(
        Long id,
        UserAccountDto userAccountDto,
        String title,
        String content,
        Set<HashtagDto> hashtagDtos,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static ArticleDto of(UserAccountDto userAccountDto, String title, String content, Set<HashtagDto> hashtagDtos) {
        return new ArticleDto(null, userAccountDto, title, content, hashtagDtos, null, null, null, null);
    }

    public static ArticleDto of(Long id, UserAccountDto userAccountDto, String title, String content, Set<HashtagDto> hashtagDtos, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDto(id, userAccountDto, title, content, hashtagDtos, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleDto from(Article entity) {
        return new ArticleDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtags().stream()
                        .map(HashtagDto::from)
                        .collect(Collectors.toUnmodifiableSet())
                ,
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public Article toEntity(UserAccount userAccount) {
        return Article.of(
                userAccount,
                title,
                content
        );
    }

}
