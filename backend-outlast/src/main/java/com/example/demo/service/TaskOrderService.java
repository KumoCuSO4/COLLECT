package com.example.demo.service;

import com.example.demo.vo.ResultVO;
import com.example.demo.vo.TaskOrderVO;
import com.github.pagehelper.PageInfo;

public interface TaskOrderService {
    ResultVO<TaskOrderVO> createOrder(TaskOrderVO order);

    PageInfo<TaskOrderVO> getDoing(Integer currPage, Integer pageSize, Integer uid);

    PageInfo<TaskOrderVO> getDone(Integer currPage, Integer pageSize, Integer uid);
}
