package countingSemaphore;

public class CountingSemaphore {
    private int maxPermissions;
    private int availablePermissions;

    public CountingSemaphore(int maxPermissions) {
        this.maxPermissions = maxPermissions;
        this.availablePermissions = maxPermissions;
    }

    public synchronized void acquire() throws InterruptedException {
        while (availablePermissions == 0) {
            wait();
        }
        availablePermissions = availablePermissions - 1;
        notifyAll();
    }

    public synchronized void release() throws InterruptedException {
        while (availablePermissions == maxPermissions) {
            wait();
        }
        availablePermissions = availablePermissions + 1;
        notifyAll();
    }

    public int getAvailablePermissions() {
        return availablePermissions;
    }

    
}
