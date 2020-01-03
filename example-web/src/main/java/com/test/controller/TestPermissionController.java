package com.test.controller;

import com.test.annotation.TestPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName TestPermissionController
 * @Description: TODO
 * @Author shibo
 * @Date 2019/12/31
 **/
@Controller
@RequestMapping("/testPermissionController")
public class TestPermissionController {
    private final static Logger logger = LoggerFactory.getLogger(ExampleController.class);
    @TestPermission(name = "测试1", role ="测试2" )
    @RequestMapping("/testPost")
    @ResponseBody
    public Object testPost(@RequestBody String para){

        logger.info("testpara-->"+para);
        return "ok";
    }
    @RequestMapping("/testPost1")
    @ResponseBody
    public Object testPost1(@RequestBody String para){

        logger.info("testpara-->"+para);
        return "ok";
    }
}
