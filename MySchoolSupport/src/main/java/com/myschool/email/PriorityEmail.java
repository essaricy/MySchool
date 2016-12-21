package com.myschool.email;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityEmail {

    private Queue<Integer> queue;

    public PriorityEmail() {
        queue = new PriorityBlockingQueue<Integer>();
    }

    public static void main(String[] args) {
        PriorityEmail priorityEmail = new PriorityEmail();
        priorityEmail.addRandom();
        //System.out.println("priorityEmail=" + priorityEmail);
        priorityEmail.consumeSequentially();
    }

    private void consumeSequentially() {
        while (!queue.isEmpty()) {
            Integer integer = queue.poll();
            System.out.println("Consumed: " + integer);
        }
    }

    private void addRandom() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int nextInt = random.nextInt(10000);
            System.out.println("Added: " + nextInt);
            queue.add(nextInt);
        }
        System.out.println(queue);
    }
}