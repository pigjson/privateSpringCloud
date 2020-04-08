package com.test.entity;

import com.test.elasticsearch.annotation.Document;
import com.test.elasticsearch.annotation.Field;
import com.test.elasticsearch.annotation.FieldType;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName PointLocation
 * @Description: TODO
 * @Author shibo
 * @Date 2020/4/2
 **/
@Data
@Document(indexName = "index_point_location")
public class PointLocation {
    @Field(type = FieldType.Double)
    private double lat;
    @Field(type = FieldType.Double)
    private double lon;
    @Field(type = FieldType.Keyword)
    private String cardNum;
    @Field(type = FieldType.Date)
    private Date positioningTime;
    @Field(type = FieldType.Double)
    private double speed;
    @Field(type = FieldType.Double)
    private double direction;
    @Field(type = FieldType.Double)
    private double ele;
    @Field(type = FieldType.Double)
    private double acceleration;

}
