package com.test.annotation;



import java.lang.annotation.*;

/**
 * @ClassName TestPermission
 * @Description: TODO
 * @Author shibo
 * @Date 2019/12/31
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TestPermission {
    //多个 参数
    String name();
    String role();
    //只有一个参数
    //    String value();
}
