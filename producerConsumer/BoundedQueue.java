package producerConsumer;

import java.util.ArrayList;
import java.util.List;

public class BoundedQueue<T> {
    private int capacity;
    private List<T> queue = new ArrayList<>();
    private int size = 0;

    public BoundedQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void addLast(T item) throws InterruptedException {
        while (size == capacity) {
            wait();
        }
        size = size + 1;
        queue.add(item);
        notifyAll();
    }

    public synchronized T removeFirst() throws InterruptedException {
        while (size == 0) {
            wait();
        }
        size = size - 1;
        T item = queue.removeFirst();

        notifyAll();
        return item;
    }

    public int getSize() {
        return size;
    }

}
