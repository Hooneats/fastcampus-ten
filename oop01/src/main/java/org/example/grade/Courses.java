package org.example.grade;

import java.util.List;

public class Courses {
    private final List<Course> courses;

    public Courses(List<Course> courses) {
        this.courses = courses;
    }

    public double multiplyCreditAndCourseGrade() {
        double multipliedCreditAndCourseGrade =
                courses.stream()
                        .mapToDouble(course -> course.multiplyCreditAndCourseGrade())
                        .sum();

        return multipliedCreditAndCourseGrade;
    }

    public double calculateTotalComplatedCredit() {
        // 수강신청 총 학점 수
        int totalCompletedCredit =
                courses.stream()
                        .mapToInt(Course::getCredit)
                        .sum();

        return totalCompletedCredit;
    }
}
