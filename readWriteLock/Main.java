package readWriteLock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // final ReadWriteLock rwLock = new ReadWriteLock(3, 3);
        final ReadWriteLockWithoutStarvation rwLock = new ReadWriteLockWithoutStarvation();
        List<Thread> allThreads = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 20; i = i + 1) {
            Thread t = Thread.ofPlatform().unstarted(() -> {
                try {
                    rwLock.acquireReadLock();
                    System.out.println("enter"+Thread.currentThread().getName());
                    Thread.sleep(random.nextLong(3000));
                    System.out.println("-----returned-----"+Thread.currentThread().getName());
                    rwLock.releaseReadLock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.setName("Read : " + i);
            allThreads.add(t);
        }

        for (int i = 0; i < 10; i = i + 1) {
            Thread t = Thread.ofPlatform().unstarted(() -> {
                try {
                    rwLock.acquireWriteLock();
                    System.out.println("enter ***** "+Thread.currentThread().getName());
                    Thread.sleep(random.nextLong(3000));
                    System.out.println("-----returned-----"+Thread.currentThread().getName());
                    rwLock.releaseWriteLock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.setName("Write : " + i);
            allThreads.add(t);
        }

        long startTime = System.nanoTime();

        for (Thread thread : allThreads) {
            thread.start();
        }

        for (Thread thread : allThreads) {
            thread.join();
        }

        System.out.println("End state: " + ", active reads = " + rwLock.getReaders() + ", active writes = "
                + rwLock.getWriters());
        System.out.println("Time : " + (System.nanoTime() - startTime) / 1000000);
    }
}
