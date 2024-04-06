package lockFreeStack;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class LockFreeStack<T> {
    private AtomicReference<StackNode<T>> head = new AtomicReference<>();

    public void push(T val) {
        StackNode<T> newHeadNode = new StackNode<>(val);

        while (true) {
            StackNode<T> currHeadNode = head.get();
            newHeadNode.next = currHeadNode;
            if (head.compareAndSet(currHeadNode, newHeadNode)) {
                break;
            } else {
                LockSupport.parkNanos(1);
            }
        }
    }

    public T pop() {
        StackNode<T> currHeadNode = head.get();
        while (currHeadNode != null) {
            if (head.compareAndSet(currHeadNode, currHeadNode.next)) {
                break;
            } else {
                LockSupport.parkNanos(1);
                currHeadNode = head.get();
            }
        }

        return currHeadNode != null ? currHeadNode.val : null;
    }
}
