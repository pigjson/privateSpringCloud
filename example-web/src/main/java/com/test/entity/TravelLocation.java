package com.test.entity;

import com.test.elasticsearch.annotation.Document;
import com.test.elasticsearch.annotation.Field;
import com.test.elasticsearch.annotation.FieldType;
import com.test.elasticsearch.annotation.Id;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName TravelLocation
 * @Description: TODO
 * @Author shibo
 * @Date 2020/4/2
 **/
@Data
@Document(indexName = "index_travel_location")
public class TravelLocation {
    @Id
    @Field(type = FieldType.Keyword)
    private String id;
    @Field(type = FieldType.Keyword)
    private String cardNum;
    @Field(type = FieldType.Date)
    private Date inboundTime;
    @Field(type = FieldType.Date)
    private Date outboundTime;
    @Field(type = FieldType.Keyword)
    private String inboundNum;
    @Field(type = FieldType.Keyword)
    private String inboundName;
    @Field(type = FieldType.Keyword)
    private String outboundNum;
    @Field(type = FieldType.Keyword)
    private String outboundName;
    @Field(type = FieldType.Object)
    private List<Site> sites;
}
