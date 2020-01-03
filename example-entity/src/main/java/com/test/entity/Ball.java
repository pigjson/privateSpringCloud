package com.test.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName Ball
 * @Description: TODO
 * @Author shibo
 * @Date 2020/1/3
 **/
@Entity
@Table(name="ball")
@Data
public class Ball {
    @Id
    @GeneratedValue
    private Integer id;
    private String red1;
    private String red2;
    private String red3;
    private String red4;
    private String red5;
    private String red6;
    private String blue;
}
