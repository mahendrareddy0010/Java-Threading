package readWriteLock;

public class ReadWriteLock {
    private int activeReads = 0;
    private int activeWrites = 0;

    private int MAX_READS = 3, MAX_WRITES = 1;

    public ReadWriteLock(int maxReads, int maxWrites) {
        MAX_READS = maxReads;
        MAX_WRITES = maxWrites;
    }

    public synchronized void readLock() throws InterruptedException {
        while (activeReads == MAX_READS) {
            wait();
        }
        activeReads = activeReads + 1;
        notifyAll();
    }

    public synchronized void readUnlock() {
        activeReads = activeReads - 1;
        notifyAll();
    }

    public synchronized void writeLock() throws InterruptedException {
        while (activeReads > 0 || activeWrites == MAX_WRITES) {
            wait();
        }
        activeWrites = activeWrites + 1;
        notifyAll();
    }

    public synchronized void writeUnlock() {
        activeWrites = activeWrites - 1;
        notifyAll();
    }

    @Override
    public String toString() {
        return "[activeReads=" + activeReads + ", activeWrites=" + activeWrites + "]"
                + Thread.currentThread().getName();
    }

    public int getActiveReads() {
        return activeReads;
    }

    public int getActiveWrites() {
        return activeWrites;
    }

}
