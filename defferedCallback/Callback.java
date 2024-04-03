package defferedCallback;

public class Callback {
    public long executeAt;
    public String message;

    public Callback(long executeAfter, String message) {
        this.executeAt = executeAfter * 1000 + System.currentTimeMillis();
        this.message = message;
    }

    public void call() {
        System.out.println("Executing in callback : " + message);
    }
}