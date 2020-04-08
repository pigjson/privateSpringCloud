package com.test;

import cn.hutool.db.nosql.redis.RedisDS;
import com.test.entity.PointLocation;
import com.test.entity.Site;
import com.test.entity.TravelLocation;
import com.test.service.EsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExampleWebApplicationTests {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private EsService esService;


    @Test
    public void testReids() {
        redisTemplate.opsForValue().set("test", "1122334455");
        redisTemplate.expire("test", 100, TimeUnit.SECONDS);
    }

    @Test
    public void redis() {
        Jedis jedis = RedisDS.create().getJedis();
        jedis.set("hi", "pizzzz");
        System.out.println(jedis.get("hi"));
    }

    @Test
    public void testEsPoint(){
        PointLocation p = new PointLocation();
        p.setAcceleration(22.222);
        p.setCardNum("123456789");
        p.setDirection(111.111);
        p.setEle(333.333);
        p.setLat(444.4444);
        p.setLon(555.555);
        p.setPositioningTime(new Date());
        p.setSpeed(66.666);

        List<PointLocation> locations = new ArrayList<>();
        locations.add(p);
        locations.add(p);
        locations.add(p);
        locations.add(p);
        esService.addOrUpdatePointLocation(locations);
    }

    @Test
    public void testEsTravel(){
        TravelLocation t = new TravelLocation();
        t.setId("123456");
        t.setCardNum("11111111");
        t.setInboundName("22222222");
        t.setInboundNum("333333");
        t.setInboundTime(new Date());
        t.setOutboundTime(new Date());
        t.setOutboundName("444444");
        t.setOutboundNum("555555");
        List<Site> sites = new ArrayList<>();
        Site s = new Site();
        s.setCost(11.11);
        s.setEle(22.22);
        s.setLat(33.33);
        s.setLon(44.44);
        s.setSiteName("666666");
        s.setSiteNum("77777");
        sites.add(s);
        Site s2 = new Site();
        s2.setCost(211.11);
        s2.setEle(222.22);
        s2.setLat(233.33);
        s2.setLon(244.44);
        s2.setSiteName("2666666");
        s2.setSiteNum("277777");
        sites.add(s2);
        t.setSites(sites);


        List<TravelLocation> list = new ArrayList<>();
        list.add(t);
        esService.addOrUpdateTravleLocation(list);
    }

    @Test
    public void testEsTravelSingle() throws InterruptedException {
        TravelLocation t = new TravelLocation();
        t.setId("232123");
        t.setCardNum("11111111");
        t.setInboundName("22222222");
        t.setInboundNum("333333");
        t.setInboundTime(new Date());
        t.setOutboundTime(new Date());
        t.setOutboundName("444444");
        t.setOutboundNum("555555");
        List<Site> sites = new ArrayList<>();
        Site s = new Site();
        s.setCost(9911.11);
        s.setEle(9922.22);
        s.setLat(9933.33);
        s.setLon(9944.44);
        s.setSiteName("99666666");
        s.setSiteNum("9977777");
        s.setDate(new Date());
        sites.add(s);
        Site s2 = new Site();
        s2.setCost(99211.11);
        s2.setEle(99222.22);
        s2.setLat(99233.33);
        s2.setLon(99244.44);
        s2.setSiteName("992666666");
        s2.setSiteNum("99277777");
        Thread.sleep(200);
        s2.setDate(new Date());
        sites.add(s2);
        t.setSites(sites);
        esService.addOrUpdateTravleLocation(t);
    }

}
