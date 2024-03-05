package com.example.demo;

import com.example.demo.po.ReportPO;
import com.example.demo.po.TaskOrderPO;
import com.example.demo.po.TaskPO;
import com.example.demo.util.Constant;
import com.example.demo.vo.TaskOrderVO;
import com.example.demo.vo.TaskVO;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@TestConfiguration
public class TestConfig {
    String testFilesPath = "src/test/testFiles/";
    String exeFile = "test_exe.rar";
    String docFile = "test_doc.md";

    @Bean(name = "taskVO1")
    public TaskVO taskVO1() {
        String exeFilePath = testFilesPath+exeFile;
        String docFilePath = testFilesPath+docFile;
        TaskVO taskVO = new TaskVO();
        try{
            taskVO.setName("COLLECT项目平台测试");
            taskVO.setDescription("测试COLLECT项目");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            taskVO.setEnd_time(format.parse("2022-07-03"));
            taskVO.setWorker_num(10);
            taskVO.setDifficulty(4);
            taskVO.setType("功能测试");
            taskVO.setEquipment("window");
            taskVO.setOthers("如果有错误，请详细描述错误内容，报错信息等内容");
            taskVO.setUser_id(6);
            taskVO.setUser_name("小明");
            taskVO.setExecutable_file(new MockMultipartFile("exe",exeFile,"", new FileInputStream(exeFilePath)));
            taskVO.setDoc_file(new MockMultipartFile("doc",docFile,"", new FileInputStream(docFilePath)));
        } catch (ParseException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return taskVO;
    }

    @Bean(name = "taskPO1")
    public TaskPO taskPO1() {
        TaskPO taskPO = new TaskPO();
        try{
            taskPO.setName("COLLECT项目平台测试");
            taskPO.setDescription("测试COLLECT项目");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            taskPO.setEnd_time(format.parse("2022-07-03"));
            taskPO.setWorker_num(10);
            taskPO.setDifficulty(4);
            taskPO.setType("功能测试");
            taskPO.setEquipment("window");
            taskPO.setOthers("如果有错误，请详细描述错误内容，报错信息等内容");
            taskPO.setUser_id(6);
            taskPO.setUser_name("小明");
            taskPO.setExecutable_file("unused");
            taskPO.setExecutable_file_uuid("unused");
            taskPO.setDoc_file("unused");
            taskPO.setDoc_file_uuid("unused");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return taskPO;
    }

    @Bean(name = "reportPO1")
    public ReportPO reportPO1() {
        ReportPO reportPO = new ReportPO();
        reportPO.setUser_id(7);
        reportPO.setTask_id(0);
        reportPO.setDescription("项目测试成功");
        reportPO.setStep("无");
        reportPO.setEquipment("window");
        reportPO.setPost_time(new Date());
        reportPO.setScore(0.0);
        return reportPO;
    }

    @Bean(name = "taskOrderVO1")
    public TaskOrderVO taskOrderVO1() {
        TaskOrderVO taskOrderVO = new TaskOrderVO();
        taskOrderVO.setId(0);
        taskOrderVO.setUser_id(7);
        taskOrderVO.setUser_name("小淼");
        taskOrderVO.setTask_id(0);
        taskOrderVO.setTask_name("COLLECT项目平台测试");
        taskOrderVO.setCreate_time(new Date());
        taskOrderVO.setStatus(Constant.TASK_FINISHED);
        return taskOrderVO;
    }

    @Bean(name = "taskOrderPO1")
    public TaskOrderPO taskOrderPO1() {
        TaskOrderPO taskOrderPO = new TaskOrderPO();
        taskOrderPO.setUser_id(7);
        taskOrderPO.setUser_name("小淼");
        taskOrderPO.setTask_name("COLLECT项目平台测试");
        taskOrderPO.setCreate_time(new Date());
        taskOrderPO.setStatus(Constant.TASK_UNFINISHED);
        return taskOrderPO;
    }

    @Bean(name = "taskOrderPO2")
    public TaskOrderPO taskOrderPO2() {
        TaskOrderPO taskOrderPO = new TaskOrderPO();
        taskOrderPO.setUser_id(7);
        taskOrderPO.setUser_name("小淼");
        taskOrderPO.setTask_name("COLLECT项目平台测试");
        taskOrderPO.setCreate_time(new Date());
        taskOrderPO.setStatus(Constant.TASK_FINISHED);
        return taskOrderPO;
    }
}
