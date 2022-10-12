package org.example.grade;

public class GradeCalculator {

    private final Courses courses;

//    public GradeCalculator(List<Course> courses) {
//        this.courses = new Courses(courses);
//    }

    public GradeCalculator(Courses courses) {
        this.courses = courses;
    }

    /**
     * 요구사항
     * • 평균학점 계산 방법 = (학점수×교과목 평점)의 합계 / 수강신청 총학점 수
     * • MVC패턴(Model-View-Controller) 기반으로 구현한다.
     * • 일급 컬렉션 사용
     */
    public double calculateGrade() {
        // (학점수 * 교과목 평점) 의 합계
//        double multipliedCreditAndCourseGrade =
//                courses.stream()
//                        // getter 를 통해서 정보를 가지고 와 처리하는 작업은 변화에 유연하지 않다(종속적이다)
//                        // .mapToDouble(course -> course.getCredit() * course.getGradeToNumber())
//                        // 개선 후 getter 를 사용하지 않는다.
//                        .mapToDouble(course -> course.multiplyCreditAndCourseGrade())
//                        .sum();

//        // 수강신청 총 학점 수
//        int totalCompletedCredit =
//                courses.stream()
//                        .mapToInt(Course::getCredit)
//                        .sum();


        // '일급 컬렉션'을 사용해보자
        double multipliedCreditAndCourseGrade = courses.multiplyCreditAndCourseGrade();
        double totalCompletedCredit = courses.calculateTotalComplatedCredit();
        // 즉 위처럼 하나의 리스트를 여러가지 작업을 할때 일급 컬렉션으로 빼면 좋다!


        return multipliedCreditAndCourseGrade / totalCompletedCredit;
    }
}
