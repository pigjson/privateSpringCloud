package com.test.dao;

import com.test.entity.Ball;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

/**
 * @ClassName BallRepository
 * @Description: TODO
 * @Author shibo
 * @Date 2020/1/3
 **/

public interface BallRepository extends JpaRepository<Ball, Integer> {
    List<Ball> findByBlue(String blue);
}
