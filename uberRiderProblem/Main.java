package uberRiderProblem;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<Thread> allThreads = new ArrayList<>();
        final UberRiderProblem uberRiderProblem = new UberRiderProblem();

        for (int i = 0; i < 6; i = i + 1) {
            Thread t = Thread.ofPlatform().unstarted(() -> {
                try {
                    uberRiderProblem.rideRepublican();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.setName("R" + i);
            allThreads.add(t);
        }

        for (int i = 0; i < 2; i = i + 1) {
            Thread t = Thread.ofPlatform().unstarted(() -> {
                try {
                    uberRiderProblem.rideRepublican();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.setName("D" + i);
            allThreads.add(t);
        }
        for (Thread thread : allThreads) {
            thread.start();
        }

        for (Thread thread : allThreads) {
            thread.join();
        }
        System.out.println(uberRiderProblem);
    }
}
