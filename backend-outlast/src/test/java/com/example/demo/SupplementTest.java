package com.example.demo;

import com.example.demo.controller.SupplementController;
import com.example.demo.mapperservice.ReportMapper;
import com.example.demo.mapperservice.SupplementMapper;
import com.example.demo.mapperservice.TaskMapper;
import com.example.demo.po.ReportPO;
import com.example.demo.po.SupplementPO;
import com.example.demo.po.TaskPO;
import com.example.demo.vo.ReportVO;
import com.example.demo.vo.ResultVO;
import com.example.demo.vo.SupplementVO;
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
public class SupplementTest {
    @Resource
    private TaskMapper taskMapper;
    @Resource
    private ReportMapper reportMapper;
    @Resource
    private SupplementController supplementController;
    @Resource
    private SupplementMapper supplementMapper;
    @Resource
    private TaskPO taskPO1;
    @Resource
    private ReportPO reportPO1;

    @Test
    public void test1() throws ParseException {
        taskMapper.insert(taskPO1);
        reportPO1.setTask_id(taskPO1.getId());
        reportMapper.insert(reportPO1);

        SupplementVO supplementVO = new SupplementVO();
        supplementVO.setUser_id(reportPO1.getUser_id());
        supplementVO.setReport_id(reportPO1.getId());
        supplementVO.setDescription("项目测试成功，不过有些许卡顿");
        supplementVO.setStep("无");
        supplementVO.setEquipment("win10设备");
        supplementVO.setPost_time(new Date());
        ResultVO<SupplementVO> record1 = supplementController.createSupplement(supplementVO);
        int successCode = 0;
        System.out.println(record1.getMsg());
        assertEquals(successCode,record1.getCode());
    }

    @Test
    public void test2() throws ParseException {
        taskMapper.insert(taskPO1);
        reportPO1.setTask_id(taskPO1.getId());
        reportMapper.insert(reportPO1);

        SupplementPO supplement = new SupplementPO();
        supplement.setUser_id(reportPO1.getUser_id());
        supplement.setReport_id(reportPO1.getId());
        supplement.setDescription("项目测试成功，不过有些许卡顿");
        supplement.setStep("无");
        supplement.setEquipment("win10设备");
        supplement.setPost_time(new Date());
        supplementMapper.insert(supplement);
        PageInfo<SupplementVO> record = supplementController.getList(reportPO1.getId(),0);
        int size = 1;
        System.out.println(record.getList().get(0));
        assertEquals(size,record.getSize());
        assertEquals(supplement.getId(),record.getList().get(0).getId());
    }

    @Test
    public void test3() throws ParseException {
        taskMapper.insert(taskPO1);
        reportPO1.setTask_id(taskPO1.getId());
        reportMapper.insert(reportPO1);

        SupplementPO supplement = new SupplementPO();
        supplement.setUser_id(reportPO1.getUser_id());
        supplement.setReport_id(reportPO1.getId());
        supplement.setDescription("项目测试成功，不过有些许卡顿");
        supplement.setStep("无");
        supplement.setEquipment("win10设备");
        supplement.setPost_time(new Date());
        supplementMapper.insert(supplement);
        SupplementVO record = supplementController.getSupplement(supplement.getId());
        System.out.println(record);
        assertEquals(supplement.getId(),record.getId());
        assertEquals(reportPO1.getUser_id(),record.getUser_id());
        assertEquals(reportPO1.getId(),record.getReport_id());
    }
}
