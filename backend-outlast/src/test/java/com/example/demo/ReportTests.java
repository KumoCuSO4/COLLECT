package com.example.demo;

import com.example.demo.controller.ReportController;
import com.example.demo.controller.TaskController;
import com.example.demo.mapperservice.ReportMapper;
import com.example.demo.mapperservice.TaskMapper;
import com.example.demo.mapperservice.TaskOrderMapper;
import com.example.demo.mapperservice.UserMapper;
import com.example.demo.po.ReportPO;
import com.example.demo.po.TaskOrderPO;
import com.example.demo.po.TaskPO;
import com.example.demo.vo.ReportVO;
import com.example.demo.vo.ResultVO;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {DemoApplication.class,TestConfig.class})
@Transactional
@Rollback(true)
public class ReportTests {
    @Resource
    private UserMapper userMapper;
    @Resource
    private TaskMapper taskMapper;
    @Resource
    private TaskOrderMapper taskOrderMapper;
    @Resource
    private ReportController reportController;
    @Resource
    private ReportMapper reportMapper;
    @Resource
    private TaskPO taskPO1;
    @Resource
    private TaskOrderPO taskOrderPO1;
    @Resource
    private ReportPO reportPO1;

    @Test
    public void test1() {
        taskMapper.insert(taskPO1);
        taskOrderPO1.setTask_id(taskPO1.getId());
        taskOrderMapper.insert(taskOrderPO1);
        reportPO1.setTask_id(taskPO1.getId());

        int successCode = 0;
        ResultVO<ReportVO> record1 = reportController.submitReport(new ReportVO(reportPO1));
        System.out.println("报告id："+record1.getData().getId());
        assertEquals(successCode,record1.getCode());

        System.out.println(userMapper.selectByUid(7));
    }

    @Test
    public void test2() {
        taskMapper.insert(taskPO1);
        reportPO1.setTask_id(taskPO1.getId());
        reportMapper.insert(reportPO1);

        PageInfo<ReportVO> record1 = reportController.getReportByReportTime(taskPO1.getId(),0);
        System.out.println(record1.getList().get(0));
        int uid = 7;
        assertEquals(uid,record1.getList().get(0).getUser_id());
        assertEquals(taskPO1.getId(),record1.getList().get(0).getTask_id());
    }

    @Test
    public void test3() throws ParseException {
        taskMapper.insert(taskPO1);
        reportPO1.setTask_id(taskPO1.getId());
        reportMapper.insert(reportPO1);

        ReportVO record1 = reportController.getReportByReportId(reportPO1.getId());
        System.out.println(record1);
        int uid = 7;
        assertEquals(uid,record1.getUser_id());
        assertEquals(taskPO1.getId(),record1.getTask_id());
    }
}
