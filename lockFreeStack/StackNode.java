package lockFreeStack;

public class StackNode<T> {
    T val;
    StackNode<T> next;
    public StackNode(T val) {
        this.val = val;
        this.next = null;
    }
}
