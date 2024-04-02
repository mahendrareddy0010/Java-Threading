package rateLimiting;

public class TokenBucketFactory {
    public static TokenBucket createTokenBucket(int maxTokens) {
        TokenBucket tokenBucket = new TokenBucket(maxTokens);
        tokenBucket.startGeneratingToken();

        return tokenBucket;
    }
}
