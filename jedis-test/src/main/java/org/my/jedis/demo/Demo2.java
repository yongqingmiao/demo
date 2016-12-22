package org.my.jedis.demo;

import redis.clients.jedis.Jedis;

public class Demo2 {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("10.37.129.13", 6379);
		jedis.auth("admin");
		Long pno = jedis.incr("pno");
		System.out.println(pno);
		jedis.close();
	}

}