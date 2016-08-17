package org.my.redis.demo.redis_test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class App {


	public static void main(String[] args) {

		String redisAddress = "10.37.129.5";
		int redisPort = 6379; 
		int redisTimeout = 2000; 

		JedisPool pool = new JedisPool(new JedisPoolConfig(), redisAddress, redisPort, redisTimeout); 
		Jedis jedis = pool.getResource(); 
		
		boolean flag = jedis.isConnected();
		System.out.println(flag);
		
		//jedis.set("www1", "法师打发第三方框架都上来看fjsdklfjklsfjaklsdjfklds"); 

		//pool.returnResource(jedis);
		
		System.out.println(jedis.get("www1"));
	}
	
	
	

	

}
