package no.hvl.dat250.jpa.polls;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

public class PollCache {

    private final JedisPool jedisPool;

    public PollCache(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * Checks if poll is cached and returns vote counts if available.
     */
    public Map<Integer, Integer> getCachedPoll(String pollId) {
        try (Jedis jedis = jedisPool.getResource()) {
            String redisKey = getRedisKey(pollId);
            Map<String, String> redisData = jedis.hgetAll(redisKey);

            if (redisData == null || redisData.isEmpty()) {
                return null; // Cache miss
            }

            Map<Integer, Integer> result = new HashMap<>();
            for (Map.Entry<String, String> entry : redisData.entrySet()) {
                result.put(Integer.parseInt(entry.getKey()), Integer.parseInt(entry.getValue()));
            }
            return result;
        }
    }

    /**
     * Stores aggregated vote counts in Redis cache.
     */
    public void cachePoll(String pollId, Map<Integer, Integer> voteCounts) {
        try (Jedis jedis = jedisPool.getResource()) {
            String redisKey = getRedisKey(pollId);
            Map<String, String> redisData = new HashMap<>();

            for (Map.Entry<Integer, Integer> entry : voteCounts.entrySet()) {
                redisData.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }

            jedis.hset(redisKey, redisData);
            // 1 hour TTL
            int ttlSeconds = 3600;
            jedis.expire(redisKey, ttlSeconds);
        }
    }

    /**
     * Invalidates the cache entry for a poll (e.g. after a vote).
     */
    public void invalidatePoll(String pollId) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(getRedisKey(pollId));
        }
    }

    private String getRedisKey(String pollId) {
        return "poll:" + pollId + ":votes";
    }
}