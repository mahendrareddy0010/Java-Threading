package rateLimiting;

public class TokenBucketWithoutProducerThread {
    private long time;
    private int MAX_TOKENS;

    public TokenBucketWithoutProducerThread(int maxTokens) {
        this.time = System.currentTimeMillis()/1000;
        this.MAX_TOKENS = maxTokens;
    }

    public synchronized long getToken() {
        long currentTime = System.currentTimeMillis()/1000;
        while(currentTime <= time) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            currentTime = System.currentTimeMillis()/1000;
        }
        if (currentTime-time > MAX_TOKENS) {
            time = currentTime - MAX_TOKENS;
        }
        long token = time;

        time = time + 1;

        return token;
    }
    
}
