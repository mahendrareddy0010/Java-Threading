package readWriteLock;

public class ReadWriteLock {
    private int readers = 0;
    private int writers = 0;

    private int MAX_READS = 3, MAX_WRITES = 1;

    public ReadWriteLock(int maxReads, int maxWrites) {
        MAX_READS = maxReads;
        MAX_WRITES = maxWrites;
    }

    public synchronized void acquireReadLock() throws InterruptedException {
        while (readers == MAX_READS) {
            wait();
        }
        readers = readers + 1;
        notifyAll();
    }

    public synchronized void releaseReadLock() {
        readers = readers - 1;
        notifyAll();
    }

    public synchronized void acquireWriteLock() throws InterruptedException {
        while (readers > 0 || writers == MAX_WRITES) {
            wait();
        }
        writers = writers + 1;
        notifyAll();
    }

    public synchronized void releaseWriteLock() {
        writers = writers - 1;
        notifyAll();
    }

    public int getReaders() {
        return readers;
    }

    public int getWriters() {
        return writers;
    }

}
