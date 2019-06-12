package com.concurrency.multithread.wait_and_notify;

import java.util.ArrayList;
import java.util.List;

class Processor {

    private List<Integer> list = new ArrayList<>();
    private final int LIMIT = 5;
    private final int BOTTOM = 0;
    private final Object lock = new Object();
    private int value = 0;

    public void produce() throws InterruptedException {

        synchronized (lock) {
            while (true) {
                if (list.size() == LIMIT) {
                    System.out.println("Waiting for removing items from the list...");
                    lock.wait();
                } else {
                    System.out.println("Adding: " + value);
                    list.add(value);
                    value++;
                    lock.notify();
                }

                Thread.sleep(500);

            }
        }
    }

    public void consume() throws InterruptedException {

        synchronized (lock) {

            while (true) {
                if (list.size() == BOTTOM) {
                    System.out.println("Waiting for adding items to the list...");
                    lock.wait();
                } else {
                    System.out.println("Removed: " + list.remove(--value));
                    lock.notify();
                }

                Thread.sleep(500);
            }
        }
    }
}


public class Wait {

    public static void main(String[] args) {

        Processor processor = new Processor();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    processor.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

    }
}
