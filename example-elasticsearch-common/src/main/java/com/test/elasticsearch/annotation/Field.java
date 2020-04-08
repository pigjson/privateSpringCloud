package com.test.elasticsearch.annotation;

import java.lang.annotation.*;

/**
 * Author: inggg
 * Date: 2019/8/14 8:38
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface Field {
    FieldType type() default FieldType.Auto;

    boolean index() default true;

    String pattern() default "";

    boolean store() default false;

    boolean fielddata() default false;

    String searchAnalyzer() default "";

    String analyzer() default "";

    String[] ignoreFields() default {};

    boolean includeInParent() default false;
}
