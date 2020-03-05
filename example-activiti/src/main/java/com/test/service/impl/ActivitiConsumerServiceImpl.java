package com.test.service.impl;

import com.test.service.ActivitiConsumerService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ActivityConsumerServiceImpl
 * @Description: TODO
 * @Author shibo
 * @Date 2020/1/17
 **/
@Service
public class ActivitiConsumerServiceImpl implements ActivitiConsumerService {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RepositoryService repositoryService;
    @Override
    public void startActivitiDemo() {
        System.out.println("method startActivityDemo begin....");

//
//        //通过流程文件名获得id，再通过id启动
//        Deployment deploy = repositoryService.createDeployment().addClasspathResource("processes/shenhe.bpmn").deploy();
//        // 获取流程定义
//        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
//                .deploymentId(deploy.getId()).singleResult();
//        // 启动流程定义，返回流程实例
//        ProcessInstance pi = runtimeService.startProcessInstanceById(processDefinition.getId());


        // 直接通过流程的id开始流程
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("shenhe");
        String processId = pi.getId();

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
