package com.test.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName Hero
 * @Description: TODO
 * @Author shibo
 * @Date 2020/1/3
 **/
@Entity
@Table(name = "hero")
@Data
public class Hero {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String type;
    private String stuID;
}
