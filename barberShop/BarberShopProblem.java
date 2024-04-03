package barberShop;

import java.util.Random;

public class BarberShopProblem {
    private int count = 0;
    private int MAX;
    private Random random = new Random();

    public BarberShopProblem(int max) {
        MAX = max;
    }

    public synchronized boolean customerCheckIn() {
        if (count == MAX) {
            System.out.println(Thread.currentThread().getName() + " : " + false);
            return false;
        }
        count++;
        System.out.println(Thread.currentThread().getName() + " : " + true);
        notifyAll();
        return true;
    }

    public void start() {
        Thread.ofVirtual().unstarted(() -> {
            try {
                barber();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void barber() throws InterruptedException {
        while (true) {
            synchronized (this) {
                while (count == 0) {
                    wait();
                }
                count--;
            }
            System.out.println("cutting customer hair");
            Thread.sleep(random.nextLong(100, 500));
        }
    }

}
