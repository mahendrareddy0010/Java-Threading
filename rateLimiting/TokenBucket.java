package rateLimiting;

import java.util.ArrayList;
import java.util.List;

public class TokenBucket {
    private int MAX_TOKENS;
    private List<Long> bucket;

    public TokenBucket(int maxTokens) {
        MAX_TOKENS = maxTokens;
        bucket = new ArrayList<>();
    }

    public void startGeneratingToken() {
        Thread t = Thread.ofVirtual().unstarted(() -> {
            try {
                generateTokens();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.setDaemon(true);
        t.start();
        
        return;
    }

    private void generateTokens() throws InterruptedException {
        while (true) {
            synchronized (this) {
                while (bucket.size() == MAX_TOKENS) {
                    wait();
                }
                bucket.add(System.currentTimeMillis() / 1000);
                notifyAll();
            }
            Thread.sleep(1000);
        }
    }

    public synchronized long getToken() throws InterruptedException {
        while (bucket.size() == 0) {
            wait();
        }

        long token = bucket.removeFirst();

        notifyAll();

        return token;
    }

}
