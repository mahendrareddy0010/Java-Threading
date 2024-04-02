package producerConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final int MAX_NUM = 100000;
    private static Random random = new Random();
    // private static BoundedQueue<Integer> bq = new BoundedQueue<>(5);
    private static BoundedQueueUsingSemaphore<Integer> bq = new BoundedQueueUsingSemaphore<>(5);

    public static void main(String[] args) throws InterruptedException {

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i = i + 1) {
            threads.add(new ProducerThread());
            threads.add(new ConsumerThread());
        }
        for(int i = 0; i < 20; i = i + 1) {
            threads.get(i).start();
        }

        for(int i = 0; i < 20; i = i + 1) {
            threads.get(i).join();
        }

        System.out.println("Queue size now : " + bq.getSize());
        
    }

    private static class ProducerThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 1000; i = i + 1) {
                try {
                    bq.addLast(random.nextInt(MAX_NUM));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ConsumerThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 1000; i = i + 1) {
                try {
                    bq.removeFirst();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
