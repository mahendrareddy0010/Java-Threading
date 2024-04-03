package defferedCallback;

import java.util.Comparator;
import java.util.PriorityQueue;

public class DefferedCallback {
    PriorityQueue<Callback> pq = new PriorityQueue<>(new Comparator<Callback>() {
        public int compare(Callback o1, Callback o2) {
            return (int) (o1.executeAt - o2.executeAt);
        }
    });
    Object lock = new Object();
    private Thread executerThread = null;

    public void start() {
        executerThread = Thread.ofVirtual().unstarted(() -> {
            try {
                processQueue();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executerThread.start();
    }

    public void processQueue() throws InterruptedException {
        long sleepFor;

        while (true) {
            synchronized (lock) {
                if (pq.isEmpty()) {
                        lock.wait(1000);
                        continue;
                }
                sleepFor = pq.peek().executeAt - System.currentTimeMillis();
                if (sleepFor > 0) {
                        lock.wait(sleepFor);                    
                } else {
                    pq.poll().call();
                }
            }
        }
    }

    public void registerCallback(Callback callback) {
        synchronized (lock) {
            if (executerThread == null) {
                start();
            }
            pq.add(callback);
            lock.notifyAll();
        }
    }

}
