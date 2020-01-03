package com.test.annotation;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @ClassName TestPermissionAspect
 * @Description: TODO
 * @Author shibo
 * @Date 2019/12/31
 **/
@Component
@Aspect
public class TestPermissionAspect implements Ordered {

    @Pointcut("execution(* com.test.controller..*(..))")
    public void test() {

    }

    @Around("test()")
    public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        TestPermission testPermission = method.getAnnotation(TestPermission.class);
        if (testPermission == null) {
            return joinPoint.proceed();
        }
        System.out.println("--->"+testPermission.name());
        System.out.println("--->"+testPermission.role());
        Object[] args = joinPoint.getArgs();
        if(null == args || args.length==0){
            return "没有收到参数！";
        }
        for(Object o:args){
            System.out.println("11111"+o);
        }
        JSONObject jsonObject = JSONUtil.parseObj(String.valueOf(args[0]));
        if(!"admin".equals(jsonObject.get("para"))){
           return "没有权限";
        }
        return joinPoint.proceed();
    }
    @Override
    public int getOrder() {
        return 0;
    }
}
