package com.powerreviews.jdbc.redis;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

/**
 * Created by dado on 4/7/16.
 */
public class JedisFactory {
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
        JedisSentinelPool pool = new JedisSentinelPool(master, sentinels);
        return pool.getResource();
    }
}
