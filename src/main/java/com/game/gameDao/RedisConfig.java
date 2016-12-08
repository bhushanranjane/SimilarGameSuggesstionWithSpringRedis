package com.game.gameDao;

import java.net.URI;
import java.net.URISyntaxException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisConfig {
	private static Jedis getConnection() throws URISyntaxException {
	    URI redisURI = new URI(System.getenv("REDIS_URL"));
	    Jedis jedis = new Jedis(redisURI);
	    return jedis;
	}
}
