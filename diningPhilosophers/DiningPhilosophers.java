package diningPhilosophers;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class DiningPhilosophers {
    private int count;
    private Semaphore[] forks;
    private Random random = new Random();

    public DiningPhilosophers(int count) {
        this.count = count;
        forks = new Semaphore[count];
        for(int i = 0; i < count; i++) {
            forks[i] = new Semaphore(1);
        }
    }

    public void start() {
        for (int i = 0; i < count; i = i + 1) {
            final int id = i;
            Thread.ofVirtual().unstarted(() -> {
                try {
                    start(id);
                } catch (InterruptedException e) {
                }
            }).start();
        }
    }

    private void start(int id) throws InterruptedException {
        while (true) {
            contemplate(id);
            eat(id);
        }
    }

    private void contemplate(int id) throws InterruptedException {
        System.out.println(id + " is thinking");
        Thread.sleep(random.nextLong(200, 1000));
    }

    private void eat(int id) throws InterruptedException {
        if (id == 0) {
            // right fork
            forks[0].acquire();
            // left fork
            forks[count - 1].acquire();
            System.out.println(id + " is eating");
            Thread.sleep(200);
            // left fork
            forks[count - 1].release();
            // right fork
            forks[0].release();
        } else {
            // left fork
            forks[id - 1].acquire();
            // right fork
            forks[id].acquire();
            System.out.println(id + " is eating");
            Thread.sleep(200);
            // right fork
            forks[id].release();
            // left fork
            forks[id - 1].release();
        }
    }
}
