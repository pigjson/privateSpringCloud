package com.test.service.impl;

import com.test.service.ActivityConsumerService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ActivityConsumerServiceImpl
 * @Description: TODO
 * @Author shibo
 * @Date 2020/1/17
 **/
@Service
public class ActivityConsumerServiceImpl implements ActivityConsumerService {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProcessEngine processEngine;

    @Override
    public void startActivityDemo() {
        System.out.println("method startActivityDemo begin....");
        File ccc = new File("D:\\workproject\\myspringcloud\\privateSpringCloud\\example-activity\\src\\main\\resources\\processes\\shenhe.bpmn");//bpmn所在位置
        InputStream fis = null;
        try{
            fis = new FileInputStream(ccc);
        }catch (Exception e){
        }
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder builder = repositoryService.createDeployment();
        builder.addInputStream("shenhe.bpmn",fis);
        builder.deploy();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("apply","kefu");
        //流程启动
        ExecutionEntity pi1 = (ExecutionEntity) runtimeService.startProcessInstanceByKey("shenhe",map);
        String processId = pi1.getId();

        System.out.println(processId);

        List<Task> tasksKefu = taskService.createTaskQuery().taskAssignee("kefu").list();
        for (Task task : tasksKefu) {
            System.out.println("taskId:" + task.getId() +
                    ",taskName:" + task.getName() +
                    ",assignee:" + task.getAssignee() +
                    ",createTime:" + task.getCreateTime());
        }

    }
}
