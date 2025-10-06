package no.hvl.dat250.jpa.polls;

import org.junit.jupiter.api.*;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PollCacheTest {

    private static JedisPool jedisPool;
    private PollCache pollCache;
    private final String testPollId = "test-poll-123";

    @BeforeAll
    static void setupPool() {
        jedisPool = new JedisPool("localhost", 6379);
    }

    @BeforeEach
    void setup() {
        pollCache = new PollCache(jedisPool);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del("poll:" + testPollId + ":votes");
        }
    }

    @Test
    void testCacheAndRetrievePoll() {
        Map<Integer, Integer> voteCounts = new HashMap<>();
        voteCounts.put(0, 10);
        voteCounts.put(1, 20);

        pollCache.cachePoll(testPollId, voteCounts);

        Map<Integer, Integer> cached = pollCache.getCachedPoll(testPollId);
        assertNotNull(cached);
        assertEquals(10, cached.get(0));
        assertEquals(20, cached.get(1));
    }

    @Test
    void testInvalidatePoll() {
        Map<Integer, Integer> voteCounts = new HashMap<>();
        voteCounts.put(0, 5);
        pollCache.cachePoll(testPollId, voteCounts);

        pollCache.invalidatePoll(testPollId);
        Map<Integer, Integer> cached = pollCache.getCachedPoll(testPollId);
        assertNull(cached);
    }

    @AfterAll
    static void tearDownPool() {
        jedisPool.close();
    }
}