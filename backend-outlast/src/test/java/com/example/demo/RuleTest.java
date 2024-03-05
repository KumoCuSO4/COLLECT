package com.example.demo;

import com.example.demo.controller.RuleController;
import com.example.demo.controller.UserController;
import com.example.demo.mapperservice.RuleMapper;
import com.example.demo.po.RulePO;
import com.example.demo.vo.ResultVO;
import com.example.demo.vo.RuleVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = DemoApplication.class)
@Transactional
@Rollback(value = true)
public class RuleTest {
    @Resource
    private RuleController ruleController;
    @Resource
    private RuleMapper ruleMapper;

    @Test
    public void test1() {
        RuleVO ruleVO = new RuleVO();
        ruleVO.setId(1);
        ruleVO.setEasy_min(1);
        ruleVO.setEasy_max(4);
        ruleVO.setMedium_min(5);
        ruleVO.setMedium_max(7);
        ruleVO.setHard_min(8);
        ruleVO.setHard_max(10);
        ruleVO.setEasy_increase(0.1);
        ruleVO.setHard_increase(0.3);
        ruleVO.setMedium_increase(0.2);
        ResultVO<RuleVO> record1 =  ruleController.updateRule(ruleVO);
        int successCode = 0;
        System.out.println(record1.getData());
        assertEquals(successCode,record1.getCode());
    }

    @Test
    public void test2() {
        RuleVO ruleVO = new RuleVO();
        ruleVO.setId(1);
        ruleVO.setEasy_min(1);
        ruleVO.setEasy_max(4);
        ruleVO.setMedium_min(5);
        ruleVO.setMedium_max(7);
        ruleVO.setHard_min(8);
        ruleVO.setHard_max(10);
        ruleVO.setEasy_increase(0.0);
        ruleVO.setHard_increase(0.2);
        ruleVO.setMedium_increase(0.1);
        System.out.println(ruleVO);
        RuleVO record1 =  ruleController.getRule();
        System.out.println(record1);
        assertEquals(record1,ruleVO);
    }

    @Test
    public void test3() {
        RuleVO ruleVO = new RuleVO();
        ruleVO.setId(1);
        ruleVO.setEasy_min(1);
        ruleVO.setEasy_max(4);
        ruleVO.setMedium_min(5);
        ruleVO.setMedium_max(7);
        ruleVO.setHard_min(8);
        ruleVO.setHard_max(10);
        ruleVO.setHard_increase(0.0);
        ruleVO.setMedium_increase(0.0);
        ruleVO.setEasy_increase(0.0);
        ResultVO<RuleVO> record1 =  ruleController.updateRule(ruleVO);
        System.out.println(record1.getData());
        double num = 0.0;
        assertEquals(num,record1.getData().getEasy_increase());
        assertEquals(num,record1.getData().getMedium_increase());
        assertEquals(num,record1.getData().getHard_increase());
    }
}
