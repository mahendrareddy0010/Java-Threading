package producerConsumer;

import java.util.concurrent.Semaphore;

public class BoundedQueueUsingSemaphore<T> {
    private int capacity;
    private int size = 0;
    private int start = 0, end = 0;
    private Object[] queue;
    private Semaphore filled, empty;
    private Object lock = new Object();

    public BoundedQueueUsingSemaphore(int capacity) {
        this.capacity = capacity;
        queue = new Object[capacity];
        filled = new Semaphore(0);
        empty = new Semaphore(capacity);
    }

    public void addLast(T item) throws InterruptedException {
        empty.acquire();
        synchronized (lock) {
            if (end == capacity) {
                end = 0;
            }
            queue[end] = item;
            end = end + 1;
            size = size + 1;
        }
        filled.release();
    }

    @SuppressWarnings("unchecked")
    public T removeFirst() throws InterruptedException {
        Object item;
        filled.acquire();
        synchronized (lock) {
            if (start == capacity) {
                start = 0;
            }
            item = queue[start];
            start = start + 1;
            size = size - 1;
        }
        empty.release();
        return (T) item;
    }

    public int getSize() {
        return size;
    }
}
