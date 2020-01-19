package com.test.service.impl;

import com.test.service.ActivitiConsumerService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
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
public class ActivitiConsumerServiceImpl implements ActivitiConsumerService {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProcessEngine processEngine;

    @Override
    public void startActivitiDemo() {
        System.out.println("method startActivityDemo begin....");
        // 开始流程
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
