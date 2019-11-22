package com.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试
 */
@Controller
@RequestMapping("/exampleController")
public class ExampleController {

    private final static Logger logger = LoggerFactory.getLogger(ExampleController.class);

    @RequestMapping("/testPost")
    @ResponseBody
    public Object testPost(){
        logger.info("我是service，我运行了");
        Map<String,Object> map = new HashMap<String,Object>(1);
        map.put("json","mysjson");
        return map;
    }
}
