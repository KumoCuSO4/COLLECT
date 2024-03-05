package com.example.demo;

import com.example.demo.controller.CommentController;
import com.example.demo.mapperservice.CommentMapper;
import com.example.demo.mapperservice.ReportMapper;
import com.example.demo.mapperservice.TaskMapper;
import com.example.demo.po.CommentPO;
import com.example.demo.po.ReportPO;
import com.example.demo.po.TaskPO;
import com.example.demo.vo.CommentVO;
import com.example.demo.vo.ResultVO;
import com.example.demo.vo.TaskOrderVO;
import com.example.demo.vo.TaskVO;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {DemoApplication.class,TestConfig.class})
@Transactional
@Rollback(true)
public class CommentTest {
    @Resource
    private TaskMapper taskMapper;
    @Resource
    private ReportMapper reportMapper;
    @Resource
    private CommentController commentController;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private TaskPO taskPO1;
    @Resource
    private ReportPO reportPO1;

    @Test
    public void test1() {
        taskMapper.insert(taskPO1);

        reportPO1.setTask_id(taskPO1.getId());
        reportMapper.insert(reportPO1);

        CommentVO comment = new CommentVO();
        comment.setUser_id(7);
        comment.setReport_id(reportPO1.getId());
        comment.setScore(5);
        comment.setDescription("这篇报告写得很有精神！");
        comment.setPost_time(new Date());
        ResultVO<CommentVO> record = commentController.createComment(comment);
        System.out.println(record.getData());
        int successCode = 0;
        assertEquals(successCode,record.getCode());
    }

    @Test
    public void test2() {
        taskMapper.insert(taskPO1);

        reportPO1.setTask_id(taskPO1.getId());
        reportMapper.insert(reportPO1);

        CommentPO comment = new CommentPO();
        comment.setUser_id(7);
        comment.setReport_id(reportPO1.getId());
        comment.setScore(5);
        comment.setDescription("这篇报告写得很有精神！");
        comment.setPost_time(new Date());
        commentMapper.insert(comment);
        PageInfo<CommentVO> record = commentController.getComments(reportPO1.getId(),0);
        System.out.println(record.getList().get(0));
        int cid = comment.getId();
        int id = reportPO1.getId();
        assertEquals(id,record.getList().get(0).getReport_id());
        assertEquals(cid,record.getList().get(0).getId());
    }
}
