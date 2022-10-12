package org.example.calculator.calcutateInterface;

public class AdditionOperator implements NewArithmeticOperator {
    @Override
    public boolean supports(String operator) {
        return "+".equals(operator);
    }

    @Override
    public int calculate(int operand1, PositiveNumber operand2) {
        return operand1 + operand2.toInt();
    }
}
