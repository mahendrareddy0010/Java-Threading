package unisexBathroom;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<Thread> allThreads = new HashSet<>();
        final UnisexBathroomWithoutStarvation bathroom = new UnisexBathroomWithoutStarvation(3);

        for (int i = 0; i < 10; i++) {
            Thread menThread = new Thread(() -> {
                try {
                    bathroom.maleUseBathroom();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            menThread.setName("Men-" + i);
            Thread womenThread = new Thread(() -> {
                try {
                    bathroom.womenUseBathroom();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            womenThread.setName("Women-" + i);
            allThreads.add(menThread);
            allThreads.add(womenThread);
        }
        for (Thread thread : allThreads) {
            thread.start();
        }
    }
}
