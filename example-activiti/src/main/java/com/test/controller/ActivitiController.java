package com.test.controller;


import com.test.service.ActivitiConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName ActivitiController
 * @Description: TODO
 * @Author shibo
 * @Date 2020/1/17
 **/
@Controller
@RequestMapping("/index")
public class ActivitiController {
    private final static Logger logger = LoggerFactory.getLogger(ActivitiController.class);

    @Autowired
    private ActivitiConsumerService activitiConsumerService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model) {
        return "index";
    }



    @ResponseBody
    @RequestMapping(value = "/startActiviti",method = RequestMethod.GET)
    public String startActiviti(Model model) {
        activitiConsumerService.startActivitiDemo();
        return "ok";
    }
}
