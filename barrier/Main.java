package barrier;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Thread> allThreads = new ArrayList<>();
        final CyclicBarrier barrier = new CyclicBarrier(5);
        for(int i = 0; i < 200000; i = i + 1) {
            Thread t = Thread.ofVirtual().unstarted(() -> {
                try {
                    barrier.done();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.setName("Thread " + i);
            allThreads.add(t);
        }

        long startTime = System.nanoTime();

        for (Thread thread : allThreads) {
            thread.start();
        }
        for (Thread thread : allThreads) {
            thread.join();
        }
        System.out.println((System.nanoTime()-startTime)/1000000);
    }
}
