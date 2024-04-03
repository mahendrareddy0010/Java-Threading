package diningPhilosophers;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int CAPCITY = 5;
        final DiningPhilosophers diningPhilosophers = new DiningPhilosophers(CAPCITY);

        diningPhilosophers.start();
        Thread.sleep(2000);
    }
}
