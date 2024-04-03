package barrier;

public class CyclicBarrier {
    private int capacity;
    private int count = 0;
    private int release = 0;
    private int shift = 0;
    private boolean acceptingRequests = true;

    public CyclicBarrier(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void done() throws InterruptedException {
        while (acceptingRequests == false) {
            wait();
        }
        count++;
        if (count == capacity) {
            acceptingRequests = false;
            release = capacity - 1;
            shift++;
        } else {
            while (release == 0) {
                wait();
            }
            release--;
        }
        System.out.println(Thread.currentThread().getName() + " in shift : " + shift);
        count--;
        if (count == 0) {
            acceptingRequests = true;
            System.out.println("--------" + Thread.currentThread().getName() + " in shift : " + shift);
        }
        notifyAll();
    }

}
