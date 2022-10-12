package org.example.calculator.calcutateInterface;

public interface NewArithmeticOperator {
    boolean supports(String operator);

    int calculate(int operand1, PositiveNumber operand2);
}
