package com.example.demo;

import com.example.demo.serviceimpl.NLPServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class NLPTests {
    @Resource
    private NLPServiceImpl nlpService;

    @Test
    public void test1() {
        //System.out.println(nlpService.calculateSimilarity("打开时发生错误，系统无响应且出现黑屏，不能正常结束", "进行测试过程中出现黑屏"));
        //System.out.println(nlpService.calculateSimilarity("苹果系统", "安卓"));
    }

    @Test
    public void test2() {
        System.out.println(nlpService.calculateAbility("分享给微信好友，被微信提示出错，检查过微信号正常登陆中，所有功能都正常的"));
    }
}
