package com.test.controller;




import com.test.api.first.FExampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 测试
 */
@Controller
@RequestMapping("/exampleController")
public class ExampleController {

    private final static Logger logger = LoggerFactory.getLogger(ExampleController.class);

    @Value("${testpara}")
    private  String testpara;

    @Autowired
    private FExampleService fExampleService;

    @RequestMapping("/testPost")
    @ResponseBody
    public Object testPost(HttpServletRequest request){

        logger.info("我是web，我运行了111");
        logger.info("testpara-->"+testpara);
        return fExampleService.queryExample();
    }

}
