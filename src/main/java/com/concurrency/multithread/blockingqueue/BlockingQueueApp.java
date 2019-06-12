package com.concurrency.multithread.blockingqueue;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class FirstWorker implements Runnable {

    private BlockingQueue<Integer> blockingQueue;

    public FirstWorker(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        int count = 0;

        while (true) {
            try {
                blockingQueue.put(count);
                System.out.println("Putting item to queue..." + count);
                count++;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class SecondWorker implements Runnable {

    private BlockingQueue<Integer> blockingQueue;

    public SecondWorker(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {

        while (true) {
            try {
                int number = blockingQueue.take();
                System.out.println("Taking item from queue..." + number);
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


public class BlockingQueueApp {

    public static void main(String[] args) {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);

        Thread t1 = new Thread(new FirstWorker(blockingQueue));
        Thread t2 = new Thread(new SecondWorker(blockingQueue));

        t1.start();
        t2.start();
    }
}
