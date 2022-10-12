package org.example.cook;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * 음식점에서 음식 주문하는 과정 구현
 *
 * * 도메인을 구성하는 객체들은 무엇이 있을지 고민
 * --> 손님 , 메뉴판, 돈까스/냉면/만두 등 메뉴 , 요리사, 요리
 * * 객체들 간의 관계를 고민
 * --> 손님 -- 메뉴판 (결정)
 * --> 손님 -- 요리사 (요청)
 * --> 요리사 -- 요리 (실행)
 * * 동적인 객체를 정적인 타입으로 추상화해서 도메인 모델링 하기
 * --> 손님 -- 손님 타입
 * --> 돈까스/냉면/만드 -- 요리 타입
 * --> 메뉴판 -- 메뉴판 타입
 * --> 메뉴 -- 메뉴 타입
 * * 협력을 설계
 * * 객체들을 포괄하는 타입에 적절한 책임 할당
 * * 구현하기
 */
public class CustomerTest {

    @DisplayName("메뉴 이름에 해당하는 요리를 주문을 한다.")
    @Test
    void orderTest() {
        Menu menu = new Menu(List.of(new MenuItem("돈까스", 5000), new MenuItem("냉면", 7000)));

        Cooking cooking = new Cooking();

        Customer customer = new Customer();
        assertThatCode(() -> customer.order("돈까스", menu, cooking))
                .doesNotThrowAnyException();
    }
}
