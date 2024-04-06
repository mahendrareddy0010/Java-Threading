package lockFreeStack;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Set<Thread> allThreads = new HashSet<>();
        final Random random = new Random();
        final LockFreeStack<Integer> lockFreeStack = new LockFreeStack<>();

        for (int i = 0; i < 10; i = i + 1) {
            allThreads.add(Thread.ofPlatform().unstarted(() -> {
                System.out.println(lockFreeStack.pop());
            }));
        }
        for (int i = 0; i < 10; i = i + 1) {
            allThreads.add(Thread.ofPlatform().unstarted(() -> {
                lockFreeStack.push(random.nextInt(100000));
            }));
        }

        for (Thread thread : allThreads) {
            thread.start();
        }
        for (Thread thread : allThreads) {
            thread.join();
        }
    }
}
