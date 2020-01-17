package com.test.controller;


import com.test.service.ActivityConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @ClassName ActivityController
 * @Description: TODO
 * @Author shibo
 * @Date 2020/1/17
 **/
@Controller
@RequestMapping("/activityController")
public class ActivityController {
    private final static Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityConsumerService activityConsumerService;

    @RequestMapping(value = "/startActivityDemo",method = RequestMethod.GET)
    public String startActivityDemo() {
        activityConsumerService.startActivityDemo();
        return "ok";
    }
}
