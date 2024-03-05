package com.example.demo.controller;

import com.example.demo.service.TaskOrderService;
import com.example.demo.util.Constant;
import com.example.demo.vo.ResultVO;
import com.example.demo.vo.TaskOrderVO;
import com.example.demo.vo.TaskVO;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/taskOrder")
public class TaskOrderController {
    @Resource
    private TaskOrderService taskOrderService;

    //接受任务
    @PostMapping("/acceptTask")
    public ResultVO<TaskOrderVO> createOrder(@RequestBody TaskOrderVO order) {
        return taskOrderService.createOrder(order);
    }

    //众包工人获取正在进行的任务
    @GetMapping("/getDoing")
    public PageInfo<TaskOrderVO> getDoing(@RequestParam Integer uid, @RequestParam Integer page) {
        return taskOrderService.getDoing(page, Constant.TASK_PAGE_SIZE, uid);
    }

    //众包工人获取已完成的任务
    @GetMapping("/getFinished")
    public PageInfo<TaskOrderVO> getDone(@RequestParam Integer uid, @RequestParam Integer page) {
        return taskOrderService.getDone(page, Constant.TASK_PAGE_SIZE, uid);
    }
}
