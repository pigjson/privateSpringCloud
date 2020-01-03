package com.test.dao;

import com.test.entity.Ball;
import com.test.entity.BallHero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName BallRepository
 * @Description: TODO
 * @Author shibo
 * @Date 2020/1/3
 **/

public interface MyBallRepository extends JpaRepository<Ball,Integer> {

    @Query(value="SELECT b FROM Ball b")
    List<Ball> getAllBall();

    // 这里的 BallHero 类用来一个用来接收多表查询结果集的类（使用 new + 完整类名构造函数）
    @Query(value = "SELECT new com.test.entity.BallHero(b, h) FROM Ball b, Hero h WHERE b.id = h.id")
    List<BallHero> getHB();
}
