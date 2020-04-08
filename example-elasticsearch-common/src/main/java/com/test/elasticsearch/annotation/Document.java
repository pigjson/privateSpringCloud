package com.test.elasticsearch.annotation;


import java.lang.annotation.*;

/**
 * Author: inggg
 * Date: 2019/8/14 8:33
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Document {
    String indexName();

    boolean useServerConfiguration() default false;

    short shards() default 5;

    short replicas() default 1;

    String refreshInterval() default "1s";

    String indexStoreType() default "fs";

    boolean createIndex() default true;
}
