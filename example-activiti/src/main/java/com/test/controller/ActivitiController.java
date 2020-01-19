package com.test.controller;


import com.test.service.ActivitiConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @ClassName ActivitiController
 * @Description: TODO
 * @Author shibo
 * @Date 2020/1/17
 **/
@Controller
@RequestMapping("/activitiController")
public class ActivitiController {
    private final static Logger logger = LoggerFactory.getLogger(ActivitiController.class);

    @Autowired
    private ActivitiConsumerService activitiConsumerService;

    @RequestMapping(value = "/startActivitiDemo",method = RequestMethod.GET)
    public String startActivitiDemo() {
        activitiConsumerService.startActivitiDemo();
        return "ok";
    }
}
