package com.fubuki.jedis;

import redis.clients.jedis.Jedis;

import java.util.List;

public class JedisTestor {
    public static void main(String[] args) {
        //ip地址和端口号
        Jedis jedis = new Jedis("192.168.189.130", 6380);
        try {
            //输入密码
            jedis.auth("123456");
            //选择1号数据库
            jedis.select(1);
//            jedis.set("name1", "fubuki");
//            String s1 = jedis.get("name1");
//            System.out.println(s1);
//            String s2 = jedis.keys("*").toString();
//            System.out.println(s2);
            //List
            jedis.del("newlist");
            jedis.rpush("newlist", new String[]{"d", "b", "c"});
            jedis.lpush("newlist", new String[]{"a", "v", "f", "g", "y"});
            List<String> lrange = jedis.lrange("newlist", 0, -1);
            System.out.println(lrange);
            jedis.rpop("newlist");
            jedis.lpop("newlist");
            lrange = jedis.lrange("newlist", 0, -1);
            System.out.println(lrange);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            jedis.close();
        }


    }
}
