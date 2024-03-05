package com.example.demo;

import com.example.demo.controller.UserController;
import com.example.demo.mapperservice.ReportMapper;
import com.example.demo.mapperservice.TaskMapper;
import com.example.demo.mapperservice.TaskOrderMapper;
import com.example.demo.po.ReportPO;
import com.example.demo.po.TaskOrderPO;
import com.example.demo.po.TaskPO;
import com.example.demo.serviceimpl.UserServiceImpl;
import com.example.demo.vo.ReportVO;
import com.example.demo.vo.ResultVO;
import com.example.demo.vo.UserVO;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {DemoApplication.class,TestConfig.class})
@Transactional
@Rollback(value = true)
public class UserTest {
    @Resource
    private UserController userController;
    @Resource
    private TaskMapper taskMapper;
    @Resource
    private TaskOrderMapper taskOrderMapper;
    @Resource
    private ReportMapper reportMapper;
    @Resource
    private MockHttpServletRequest request;
    @Resource
    private MockHttpServletResponse response;
    @Resource
    private TaskPO taskPO1;
    @Resource
    private TaskOrderPO taskOrderPO1;
    @Resource
    private ReportPO reportPO1;

    @Before
    public void setUp(){
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        response = new MockHttpServletResponse();
    }

    @Test
    public void test1() {
        UserVO test1 = new UserVO();
        test1.setUid(0);
        test1.setUname("小7");
        test1.setPassword("123456");
        test1.setUser_role(1);
        test1.setPhone("12345678910");
        test1.setEmail("123456@qq.com");
        //UserVO test2 = new UserVO();
        //test2.setUid(0);
        //test2.setUname("小房");
        //test2.setPassword("123456");
        //test2.setUser_role(1);
        int successCode = 0;
        ResultVO<UserVO> record1 = userController.register(test1);
        //ResultVO<UserVO> record2 = userController.register(test2);

        System.out.println("小7的ID："+record1.getData().getUid());
        assertEquals(successCode,record1.getCode());
        //assertEquals(successCode,record2.getCode());
    }

    @Test
    public void test2() {
        UserVO test1 = new UserVO();
        test1.setUid(6);
        test1.setUname("小明");
        test1.setPassword("123456");
        test1.setUser_role(1);
        /*UserVO test2 = new UserVO();
        test2.setUid(7);
        test2.setUname("小淼");
        test2.setPassword("654321");
        test2.setUser_role(2);*/
        int successCode = 0;
        ResultVO<UserVO> record1 = userController.login(request,test1);
        /*ResultVO<UserVO> record2 = userController.login("小明","123456");*/

        /*System.out.println("小淼的ID："+record2.getData().getUid());*/
        assertEquals(successCode,record1.getCode());
        assertEquals(test1.getUname(),record1.getData().getUname());
        assertEquals(test1.getPassword(),record1.getData().getPassword());
        /*assertEquals(successCode,record2.getCode());*/
    }

    @Test
    public void test3() {
        int successCode = 0;
        ResultVO<UserVO> record1 = userController.logout(request);
        assertEquals(successCode,record1.getCode());
    }

    @Test
    public void test4() {
        UserVO test1 = new UserVO();
        test1.setUid(6);
        test1.setUname("小明");
        test1.setPassword("123456");
        test1.setPhone("188");
        test1.setEmail("11111@smail.nju.edu.cn");
        test1.setDescription("ming");
        test1.setEquipment("2");
        test1.setPreference("3");
        int successCode = 0;
        String des = "ming";
        ResultVO<UserVO> record1 = userController.updateInformation(test1);
        System.out.println(record1.getData());
        assertEquals(successCode,record1.getCode());
        //assertEquals(test1.getPhone(),record1.getData().getPhone());
        //assertEquals(test1.getEmail(),record1.getData().getEmail());
        assertEquals(test1.getDescription(),record1.getData().getDescription());
    }

    @Test
    public void test5() {
        taskMapper.insert(taskPO1);
        taskOrderPO1.setTask_id(taskPO1.getId());
        taskOrderMapper.insert(taskOrderPO1);
        reportPO1.setTask_id(taskPO1.getId());
        reportMapper.insert(reportPO1);

        UserVO record = userController.getInformation(7).getUserVO();
        assertEquals(record.getPreference(),taskPO1.getType());
        assertEquals(record.getEquipment(),taskPO1.getEquipment());
        System.out.println(userController.getInformation(7));
    }
}
