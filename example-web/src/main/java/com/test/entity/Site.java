package com.test.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName Site
 * @Description: TODO
 * @Author shibo
 * @Date 2020/4/2
 **/
@Data
public class Site {
    private String siteNum;
    private String siteName;
    private double lat;
    private double lon;
    private double ele;
    private double cost;
    private Date date;
}
