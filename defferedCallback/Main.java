package defferedCallback;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        DefferedCallback differedCallback = new DefferedCallback();
        // differedCallback.start(); this is not required i am doing it in register callback
        List<Thread> allThreads = new ArrayList<>();

        for(int i =0; i < 10; i = i + 1) {
            Thread t = new Thread(() -> {
            differedCallback.registerCallback(new Callback(1, "Thread : " + Thread.currentThread().getName()));
            });
            t.setName("Thread_"+i);
            t.start();
            Thread.sleep(500);
            allThreads.add(t);
        }
        for (Thread thread : allThreads) {
            thread.join();
        }
        Thread.sleep(3000);
    }
}
