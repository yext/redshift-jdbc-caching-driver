package com.powerreviews.jdbc.redis;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by dado on 4/7/16.
 */
public class JedisFactory {
    private static JedisSentinelPool pool;
    private static int MAX_IDLE_POOL_CONNECTIONS = 128;
    private static int MAX_TOTAL_POOL_CONNECTIONS = 128;

    public Jedis createJedisClient(String url, Integer port) {
        if(StringUtils.isEmpty(url)) {
            return null;
        }
        return port != null ? new Jedis(url, port) : new Jedis(url);
    }

    public Jedis createJedisSentinelClient(String master, Set<String> sentinels) {
        if(StringUtils.isEmpty(master)) {
            return null;
        }
        if(pool == null) {
            initPool(master, sentinels);
        }
        return pool.getResource();
    }

    private void initPool(String master, Set<String> sentinels) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(MAX_TOTAL_POOL_CONNECTIONS);
        poolConfig.setMaxIdle(MAX_IDLE_POOL_CONNECTIONS);
        pool = new JedisSentinelPool(master, sentinels, poolConfig);
    }
}
