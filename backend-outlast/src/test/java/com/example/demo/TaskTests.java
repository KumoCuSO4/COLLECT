package com.example.demo;

import com.example.demo.controller.TaskController;
import com.example.demo.mapperservice.TaskMapper;
import com.example.demo.mapperservice.UserMapper;
import com.example.demo.po.TaskPO;
import com.example.demo.vo.ResultVO;
import com.example.demo.vo.TaskVO;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {DemoApplication.class,TestConfig.class})
@Transactional
@Rollback(value = true)
public class TaskTests {
    @Resource
    private TaskController taskController;
    @Resource
    private TaskMapper taskMapper;
    @Resource
    private UserMapper userMapper;

    @Resource
    private TaskVO taskVO1;
    @Resource
    private TaskPO taskPO1;

    @Test
    public void test1() {
        int successCode = 0;
        ResultVO<TaskVO> record1 = taskController.createTask(taskVO1);
        System.out.println("项目名：" + record1.getData().getName());
        assertEquals(successCode, record1.getCode());
        assertEquals(taskVO1.getName(),record1.getData().getName());
        assertEquals(taskVO1.getDescription(),record1.getData().getDescription());
        /*taskVO1.setId(0);
        taskVO1.setType("安全测试");
        ResultVO<TaskVO> record2 = taskController.createTask(taskVO1);
        userMapper.updatePreference(taskVO1.getUser_id(),taskMapper.selectTypeByUid(taskVO1.getUser_id()));
        System.out.println(userMapper.selectByUid(taskVO1.getUser_id()));*/
    }

    @Test
    public void test2() {
        taskMapper.insert(taskPO1);
        TaskVO record1 = taskController.getTaskById(taskPO1.getId());
        String name = "COLLECT项目平台测试";
        int uid = 6;
        assertEquals(taskPO1.getName(), record1.getName());
        assertEquals(taskPO1.getUser_id(), record1.getUser_id());
    }

    @Test
    public void test3() {
        taskMapper.insert(taskPO1);
        PageInfo<TaskVO> record1 = taskController.getAllTask(0);
        String name = "COLLECT项目平台测试";
        System.out.println(record1.getList().size());
        assertEquals(name,record1.getList().get(0).getName());
    }

    @Test
    public void test4() {
        taskMapper.insert(taskPO1);
        PageInfo<TaskVO> record1 = taskController.getList(7,0);
        String name = "COLLECT项目平台测试";
        int size = 1;
        System.out.println(record1.getSize());
        assertEquals(name,record1.getList().get(0).getName());
    }

    @Test
    public void test5() {
        taskMapper.insert(taskPO1);
        PageInfo<TaskVO> record1 = taskController.getCreated(6,0);
        String name = "COLLECT项目平台测试";
        System.out.println(record1.getSize());
        System.out.println(record1.getList().get(0));
        assertEquals(name,record1.getList().get(0).getName());
    }
}
