package readWriteLock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final ReadWriteLock rwLock = new ReadWriteLock(3, 1);
        List<Thread> allThreads = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 1000; i = i + 1) {
            Thread t = Thread.ofVirtual().unstarted(() -> {
                try {
                    rwLock.readLock();
                    Thread.sleep(random.nextLong(10));
                    rwLock.readUnlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.setName("Thread : " + i);
            allThreads.add(t);
        }

        for(int i = 0; i < 10; i = i + 1) {
        Thread t = Thread.ofVirtual().unstarted(() -> {
        try {
        rwLock.writeLock();
        System.out.println("write lock : " + Thread.currentThread().getName());
        Thread.sleep(random.nextLong(2));
        rwLock.writeUnlock();
        } catch (InterruptedException e) {
        e.printStackTrace();
        }
        });
        t.setName("Thread : "+ i);
        allThreads.add(t);
        }

        long startTime = System.nanoTime();

        for (Thread thread : allThreads) {
            thread.start();
        }

        for (Thread thread : allThreads) {
            thread.join();
        }

        System.out.println("End state: " + ", active reads = " + rwLock.getActiveReads() + ", active writes = "
                + rwLock.getActiveWrites());
        System.out.println("Time : " + (System.nanoTime() - startTime) / 1000000);
    }
}
