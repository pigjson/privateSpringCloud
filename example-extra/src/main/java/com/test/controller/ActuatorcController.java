package com.test.controller;


import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.test.util.RestTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/actuator")
public class ActuatorcController {

    private final static Logger logger = LoggerFactory.getLogger(ActuatorcController.class);

    @Resource
    private EurekaClient eurekaClient;

    @RequestMapping(value = "/up", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String up() {
        ApplicationInfoManager applicationInfoManager = eurekaClient.getApplicationInfoManager();
        InstanceInfo instanceInfo = applicationInfoManager.getInfo();
        logger.info(instanceInfo.getAppName() + "服务上线");
        Map<String, String> map = new HashMap<String, String>();
        map.put("status", "UP");
        RestTemplateUtil.postJsonForObject(instanceInfo.getHomePageUrl() + "actuator/service-registry", map, Map.class);
        return instanceInfo.getAppName() + "服务上线成功";
    }

    @RequestMapping(value = "/down", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String down() {
        ApplicationInfoManager applicationInfoManager = eurekaClient.getApplicationInfoManager();
        InstanceInfo instanceInfo = applicationInfoManager.getInfo();
        logger.info(instanceInfo.getAppName() + "服务下线");
        Map<String, String> map = new HashMap<String, String>();
        map.put("status", "DOWN");
        RestTemplateUtil.postJsonForObject(instanceInfo.getHomePageUrl() + "actuator/service-registry", map, Map.class);
        return instanceInfo.getAppName() + "服务下线成功";
    }
}
