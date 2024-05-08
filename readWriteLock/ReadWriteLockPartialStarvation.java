package readWriteLock;

public class ReadWriteLockPartialStarvation {
    private static enum Turn {
        NONE, READS, WRITES
    }

    private final int MAX = 3;
    private Turn turn = Turn.NONE;
    private int readers = 0, writers = 0;
    private int waitingReaders = 0;
    private int waitingWriters = 0;

    public synchronized void acquireReadLock() throws InterruptedException {
        while ((turn == Turn.WRITES && waitingWriters > 0) || writers > 0 || readers == MAX) {
            waitingReaders++;
            wait();
            waitingReaders--;
        }
        readers++;
        if (readers == MAX) {
            turn = Turn.WRITES;
        } else {
            turn = Turn.READS;
        }
        notifyAll();
        
        if (readers > 0 && writers > 0) {
            System.out.println("%%%%%%%%%%%%%%%%%%%%");
        }
    }

    public synchronized void releaseReadLock() {
        readers--;
        notifyAll();
    }

    public synchronized void acquireWriteLock() throws InterruptedException {
        while ((turn == Turn.READS && waitingReaders > 0) || readers > 0 || writers == MAX) {
            waitingWriters++;
            wait();
            waitingWriters--;
        }
        writers++;
        if (writers == MAX) {
            turn = Turn.READS;
        } else {
            turn = Turn.WRITES;
        }
        notifyAll();

        if (readers > 0 && writers > 0) {
            System.out.println("%%%%%%%%%%%%%%%%%%%%");
        }
    }

    public synchronized void releaseWriteLock() {
        writers--;
        notifyAll();
    }

    public int getReaders() {
        return readers;
    }

    public int getWriters() {
        return writers;
    }

}
