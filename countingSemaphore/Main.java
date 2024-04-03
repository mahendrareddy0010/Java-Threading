package countingSemaphore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Thread> allThreads = new ArrayList<>();
        Random random = new Random();
        final CountingSemaphore cs = new CountingSemaphore(2);

        for (int i = 0; i < 10; i = i  + 1) {
            allThreads.add(new Thread(() -> {
                for (int j = 0; j < 100; j = j + 1) {
                    try {
                        cs.acquire();
                        Thread.sleep(random.nextLong(10,100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }
        for (int i = 0; i < 10; i = i  + 1) {
            allThreads.add(new Thread(() -> {
                for (int j = 0; j < 100; j = j + 1) {
                    try {
                        cs.release();
                        Thread.sleep(random.nextLong(10,100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }

        for (Thread thread : allThreads) {
            thread.start();
        }
        for (Thread thread : allThreads) {
            thread.join();
        }

        
        System.out.println(cs.getAvailablePermissions());
    }
}
