package barberShop;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Random random = new Random();
        final BarberShopProblem barberShopProblem = new BarberShopProblem(3);
        barberShopProblem.start();
        List<Thread> allThreads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Thread t = Thread.ofPlatform().unstarted(() -> {
                barberShopProblem.customerCheckIn();
            });
            t.setName("Thread-" + i);
            allThreads.add(t);
        }
        for (Thread thread : allThreads) {
            thread.start();
            Thread.sleep(50);
        }
        for (Thread thread : allThreads) {
            thread.join();
        }
        Thread.sleep(5000);
    }
}
