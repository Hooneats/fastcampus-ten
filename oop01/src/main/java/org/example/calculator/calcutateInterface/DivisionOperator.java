package org.example.calculator.calcutateInterface;

import org.example.calculator.calcutateInterface.NewArithmeticOperator;

public class DivisionOperator implements NewArithmeticOperator {
    @Override
    public boolean supports(String operator) {
        return "/".equals(operator);
    }

    @Override
    public int calculate(int operand1, PositiveNumber operand2) {
//        if (operand2 == 0) {
//            throw new IllegalArgumentException("0으로는 나눌 수 없습니다.");
//        }
        return operand1 / operand2.toInt();
    }
}
