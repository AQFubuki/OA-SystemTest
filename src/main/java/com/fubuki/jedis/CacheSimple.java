package com.fubuki.jedis;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CacheSimple {
    public CacheSimple() {
        Jedis jedis = new Jedis("192.168.189.130", 6380);
        try {
            jedis.auth("123456");
            //选择1号数据库
            jedis.select(1);

            List<Goods> goodsList = new ArrayList<Goods>();
            goodsList.add(new Goods("风扇", 13,
                    10.3F, "就只是风扇"));
            goodsList.add(new Goods("挖掘机", 14,
                    43566.65F, "就只是挖掘机"));
            goodsList.add(new Goods("猜到", 15,
                    33F, "就只是猜到"));

            for (Goods goods : goodsList) {
                String json = JSON.toJSONString(goods);
                System.out.println(json);
                String keys = "goods:" + goods.getGoodsId();
                jedis.set(keys, json);
            }
            Set<String> keys = jedis.keys("goods:*");
            for (String key : keys) {
                System.out.println(key);
                System.out.println(jedis.get(key));
                Goods g = JSON.parseObject(jedis.get(key), Goods.class);
                System.out.println("ID:" + g.getGoodsId());
                System.out.println("名称：" + g.getGoodsName());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            jedis.close();
        }
    }
}
