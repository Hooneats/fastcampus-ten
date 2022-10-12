package org.example.counter;

public class Counter implements Runnable {

    private int count = 0;

    public void increment() {
        this.count++;
    }

    public void decrement() {
        this.count--;
    }

    public int getValue() {
        return this.count;
    }

    @Override
    public void run() {

        // RaceCondition 을 방지하기 위한 synchronized 처리
        synchronized (this) {
            this.increment();
            System.out.println("Value for thread After increment" + Thread.currentThread().getName() +
                    " " + this.getValue());
            this.decrement();
            System.out.println("Value for Thread at last " + Thread.currentThread().getName() +
                    " " + this.getValue());
        }
    }
}
