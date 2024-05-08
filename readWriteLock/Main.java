package readWriteLock;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // final ReadWriteLock rwLock = new ReadWriteLock(3, 3);
        // final ReadWriteLockPartialStarvation rwLock = new ReadWriteLockPartialStarvation();
        final ReadWriteLockWithoutStarvation rwLock = new ReadWriteLockWithoutStarvation();
        Set<Thread> allThreads = new HashSet<>();

        for (int i = 0; i < 1000; i = i + 1) {
            Thread t = Thread.ofPlatform().unstarted(() -> {
                try {
                    rwLock.acquireReadLock();
                    rwLock.releaseReadLock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.setName("Read : " + i);
            allThreads.add(t);
        }

        for (int i = 0; i < 1000; i = i + 1) {
            Thread t = Thread.ofPlatform().unstarted(() -> {
                try {
                    rwLock.acquireWriteLock();
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
