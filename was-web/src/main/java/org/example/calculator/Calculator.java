package org.example.calculator;

import org.example.calculator.calcutateInterface.*;

import java.util.List;

public class Calculator {

    private static final List<NewArithmeticOperator> arithmeticOperators =
            List.of(new AdditionOperator(), new SubtractionOperator(), new MultiplicationOperator(), new DivisionOperator());


    public static int calculate(int operand1, String operator, int operand2) {
        // enum 사용
//        return ArithmeticOperator.calculate(operand1, operator, operand2);
        // interface 사용
        return arithmeticOperators.stream()
                .filter(arithmeticOperators -> arithmeticOperators.supports(operator))
                .findFirst()
                .map(arithmeticOperator -> arithmeticOperator.calculate(
                        operand1,
                        new PositiveNumber(operand2)
                ))
                .orElseThrow(() -> new IllegalArgumentException("올바른 사칙연산이 아닙니다."));
    }
}
