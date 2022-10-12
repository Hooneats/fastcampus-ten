package org.example.grade;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 요구사항
 * • 평균학점 계산 방법 = (학점수×교과목 평점)의 합계 / 수강신청 총학점 수
 * • MVC패턴(Model-View-Controller) 기반으로 구현한다.
 * • 일급 컬렉션 사용
 *
 * 도메인을 구성하는 객체들은 무엇이 있을지 고민
 * 객체들 간의 관계를 고민
 * 동적인 객체를 정적인 타입으로 추상화해서 도메인 모델링 하기
 * 협력을 설계
 * 객체들을 포괄하는 타입에 적절한 책임 할당
 * 구현하기
 *
 *  학점 계산기 도메인 : 이수한 과목, 학점 계산기 --> 객체들간의 관계 고민
 *  이수한 과목 : 객체지향 프로그래밍, 자료구조, 중국어 회화 --> 동적인 세가지 객체를 정적인 타입으로 추상화 -> 과목(코스) 클래스
 *  이수한 과목(파라미터)을 전달하여 평균학점 계산 요청 --> 학점 계산기에게 요청 -> 학점수*교과목 평점의 합계 , -> 과목(코스) 요청 -객체들을 포괄하는 타입에 적절한 책임 할당
 *                                                                      수강신청 총학점 수        -> 과목(코스) 요청 -객체들을 포괄하는 타입에 적절한 책임 할당
청*/
public class GradeCalculatorTest {

    @DisplayName("평균 학점을 계산한다.")
    @Test
    void calculateGradeTest() {
        List<Course> courses = List.of(
                new Course("OOP", 3, "B+"),
                new Course("자료구조", 3, "B")
        );
//        GradeCalculator gradeCalculator = new GradeCalculator(courses);
        GradeCalculator gradeCalculator = new GradeCalculator(new Courses(courses));
        double gradeResult =  gradeCalculator.calculateGrade(); // 성적계산

        assertThat(gradeResult).isEqualTo(3.25);
    }
}
