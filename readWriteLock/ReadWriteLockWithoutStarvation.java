package readWriteLock;

public class ReadWriteLockWithoutStarvation {
    private static enum Turn {
        NONE, READS, WRITES
    }

    private final int MAX = 3;
    private Turn turn = Turn.NONE;
    private int readers = 0, writers = 0;
    private int waitingReaders = 0;
    private int waitingWriters = 0;
    private int readersServed = 0;
    private int writersServed = 0;

    public synchronized void acquireReadLock() throws InterruptedException {
        while (turn == Turn.WRITES || writers > 0 || readers == MAX) {
            waitingReaders++;
            wait();
            waitingReaders--;
        }
        readers++;
        readersServed++;
        notifyAll();
        
        if (readers > 0 && writers > 0) {
            System.out.println("%%%%%%%%%%%%%%%%%%%%");
        }
    }

    public synchronized void releaseReadLock() {
        readers--;
        if (readersServed == MAX || readers == 0) {
            if (waitingWriters > 0) {
                turn = Turn.WRITES;
            }
            else {
                turn = Turn.NONE;
            }
            readersServed = 0;
        }
        notifyAll();
    }

    public synchronized void acquireWriteLock() throws InterruptedException {
        while ((turn == Turn.READS && waitingReaders > 0) || readers > 0 || writers == MAX) {
            waitingWriters++;
            wait();
            waitingWriters--;
        }
        writers++;
        writersServed++;
        notifyAll();

        if (readers > 0 && writers > 0) {
            System.out.println("%%%%%%%%%%%%%%%%%%%%");
        }
    }

    public synchronized void releaseWriteLock() {
        writers--;
        if (writersServed == MAX || writers == 0) {
            if (waitingReaders > 0) {
                turn = Turn.READS;
            } else {
                turn = Turn.NONE;
            }
            writersServed = 0;
        }
        notifyAll();
    }

    public int getReaders() {
        return readers;
    }

    public int getWriters() {
        return writers;
    }

}
