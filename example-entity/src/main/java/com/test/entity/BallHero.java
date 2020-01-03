package com.test.entity;

import lombok.Data;

/**
 * @ClassName BallHero
 * @Description: TODO
 * @Author shibo
 * @Date 2020/1/3
 **/
@Data
public class BallHero {
    private Ball ball;
    private Hero hero;

    public BallHero(Ball ball, Hero hero) {
        this.ball = ball;
        this.hero = hero;
    }
}
