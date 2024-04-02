package rateLimiting;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static TokenBucketWithoutProducerThread tb = new TokenBucketWithoutProducerThread(5);
    // private static TokenBucket tb = TokenBucketFactory.createTokenBucket(5);

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 20; i = i + 1) {
            threads.add(Thread.ofPlatform().unstarted(() -> {
                long token;
                for(int j = 0; j < 10; j = j + 1) {
                    //  use try block for TokenBucket class use
                    // try {
                        token = tb.getToken();
                        System.out.println("Thread : " + Thread.currentThread() + " got token : " + token);
                    // } catch (InterruptedException e) {
                    //     e.printStackTrace();
                    // }
                }
            }));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

    }
}
