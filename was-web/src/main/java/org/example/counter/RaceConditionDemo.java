package org.example.counter;

public class RaceConditionDemo {

    public static void main(String[] args) {
        Counter counter = new Counter();

        /**
         * // 하나의 자원인 counter 를 공유하게되면 RaceCondition 즉 원치않는 결과가 나올 수 있다.
         *   ㄴ  RaceCondition 은 여러 쓰레드가 하나의 자원에 동시에 접근하기 위해 경쟁하는 상태를 말한다.
         */
        Thread t1 = new Thread(counter, "Thread-1");
        Thread t2 = new Thread(counter, "Thread-2");
        Thread t3 = new Thread(counter, "Thread-3");

        t1.start();
        t2.start();
        t3.start();
    }
}
