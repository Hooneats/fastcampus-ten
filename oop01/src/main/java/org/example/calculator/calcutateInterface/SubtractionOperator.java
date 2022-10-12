package org.example.calculator.calcutateInterface;

import org.example.calculator.calcutateInterface.NewArithmeticOperator;

public class SubtractionOperator implements NewArithmeticOperator {
    @Override
    public boolean supports(String operator) {
        return "-".equals(operator);
    }

    @Override
    public int calculate(int operand1, PositiveNumber operand2) {
        return operand1 - operand2.toInt();
    }
}
