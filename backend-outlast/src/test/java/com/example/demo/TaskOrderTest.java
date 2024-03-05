package com.example.demo;

import com.example.demo.controller.TaskController;
import com.example.demo.controller.TaskOrderController;
import com.example.demo.mapperservice.TaskMapper;
import com.example.demo.mapperservice.TaskOrderMapper;
import com.example.demo.po.ReportPO;
import com.example.demo.po.TaskOrderPO;
import com.example.demo.po.TaskPO;
import com.example.demo.util.Constant;
import com.example.demo.vo.ResultVO;
import com.example.demo.vo.TaskOrderVO;
import com.example.demo.vo.TaskVO;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {DemoApplication.class,TestConfig.class})
@Transactional
@Rollback(value = true)
public class TaskOrderTest {
    @Resource
    private TaskOrderController taskOrderController;
    @Resource
    private TaskOrderMapper taskOrderMapper;
    @Resource
    private TaskController taskController;
    @Resource
    private TaskMapper taskMapper;
    @Resource
    private TaskPO taskPO1;
    @Resource
    private TaskOrderVO taskOrderVO1;
    @Resource
    private TaskOrderPO taskOrderPO1;
    @Resource
    private TaskOrderPO taskOrderPO2;

    @Test
    public void test1() {
        taskMapper.insert(taskPO1);

        taskOrderVO1.setTask_id(taskPO1.getId());

        ResultVO<TaskOrderVO> record1 = taskOrderController.createOrder(taskOrderVO1);
        int successCode = 0;
        System.out.println(record1.getMsg());
        assertEquals(successCode,record1.getCode());
    }

    @Test
    public void test2() {
        taskMapper.insert(taskPO1);

        taskOrderPO1.setTask_id(taskPO1.getId());
        taskOrderMapper.insert(taskOrderPO1);
        PageInfo<TaskOrderVO> record1 = taskOrderController.getDoing(7,0);
        String name = "COLLECT项目平台测试";
        System.out.println(record1.getList().get(0));
        assertEquals(name,record1.getList().get(0).getTask_name());
    }

    @Test
    public void test3() {
        taskMapper.insert(taskPO1);

        taskOrderPO2.setTask_id(taskPO1.getId());
        taskOrderMapper.insert(taskOrderPO2);
        PageInfo<TaskOrderVO> record1 = taskOrderController.getDone(7,0);
        String name = "COLLECT项目平台测试";
        System.out.println(record1.getList().get(0));
        assertEquals(name,record1.getList().get(0).getTask_name());
    }

    @Test
    public void test4() {
        taskMapper.insert(taskPO1);

        taskOrderPO1.setTask_id(taskPO1.getId());
        taskOrderMapper.insert(taskOrderPO1);

        taskPO1.setId(0);
        taskMapper.insert(taskPO1);
        //众包工人接取任务后的任务广场测试
        PageInfo<TaskVO> record0 = taskController.getList(7,0);
        System.out.println(record0.getList().get(0));
        assertEquals(taskPO1.getId(),record0.getList().get(0).getId());

        taskPO1.setId(0);
        taskMapper.insert(taskPO1);
        PageInfo<TaskVO> record1 = taskController.getList(7,0);
        System.out.println(record1.getList().get(1));
        assertEquals(taskPO1.getId(),record1.getList().get(1).getId());

        taskPO1.setId(0);
        taskMapper.insert(taskPO1);
        taskPO1.setId(0);
        taskMapper.insert(taskPO1);
        taskPO1.setId(0);
        taskMapper.insert(taskPO1);
        taskPO1.setId(0);
        taskMapper.insert(taskPO1);
        taskPO1.setId(0);
        taskMapper.insert(taskPO1);
        PageInfo<TaskVO> record2 = taskController.getList(7,0);
        System.out.println(record2.getSize());
        PageInfo<TaskVO> record3 = taskController.getList(7,2);
        assertEquals(taskPO1.getId(),record3.getList().get(0).getId());
        System.out.println(record3.getSize());
    }
}
