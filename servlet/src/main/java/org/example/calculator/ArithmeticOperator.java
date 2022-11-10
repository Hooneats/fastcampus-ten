package org.example.calculator;

import java.util.Arrays;

public enum ArithmeticOperator {
    ADDITION("+"){
        @Override
        public int arithmeticCalculate(int operand1, int operand2) {
            return operand1 + operand2;
        }
    },
    SUBTRACTION("-") {
        @Override
        public int arithmeticCalculate(int operand1, int operand2) {
            return operand1 - operand2;
        }
    },
    MULTIPLICATION("*") {
        @Override
        public int arithmeticCalculate(int operand1, int operand2) {
            return operand1 * operand2;
        }
    },
    DIVISION("/") {
        @Override
        public int arithmeticCalculate(int operand1, int operand2) {
            return operand1 / operand2;
        }
    };

    private final String operator;

    ArithmeticOperator(String operator) {
        this.operator = operator;
    }

    protected abstract int arithmeticCalculate(final int operand1, final int operand2);

    // 외부에 노출 사용 ArithmeticOperator.calculate(operand1, operator, operand2);
    public static int calculate(int operand1, String operator, int operand2) {
        ArithmeticOperator arithmeticOperator = Arrays.stream(values())
                .filter(value -> value.operator.equals(operator))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 사칙 연산이 아닙니다."));

        int result = arithmeticOperator.arithmeticCalculate(operand1, operand2);
        return result;
    }
}
