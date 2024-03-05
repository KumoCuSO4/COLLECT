package com.example.demo.serviceimpl;

import com.example.demo.controller.TaskController;
import com.example.demo.mapperservice.TaskOrderMapper;
import com.example.demo.po.TaskOrderPO;
import com.example.demo.po.TaskPO;
import com.example.demo.service.TaskOrderService;
import com.example.demo.util.Constant;
import com.example.demo.util.PageInfoUtil;
import com.example.demo.vo.ResultVO;
import com.example.demo.vo.TaskOrderVO;
import com.example.demo.vo.TaskVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class TaskOrderServiceImpl implements TaskOrderService {
    @Resource
    TaskOrderMapper taskOrderMapper;
    @Resource
    TaskController taskController;

    @Override
    public ResultVO<TaskOrderVO> createOrder(TaskOrderVO order) {
        order.setCreate_time(new Date());
        order.setStatus(Constant.TASK_UNFINISHED);
        TaskOrderPO taskOrder = new TaskOrderPO(order);
        if(taskOrderMapper.insert(taskOrder) > 0){
            return new ResultVO<TaskOrderVO>(Constant.REQUEST_SUCCESS, "任务接受成功", new TaskOrderVO(taskOrder));
        }
        return new ResultVO<>(Constant.REQUEST_FAIL, "发生未知错误");
    }

    @Override
    public PageInfo<TaskOrderVO> getDoing(Integer currPage, Integer pageSize, Integer uid) {
        if(currPage==null || currPage<1) currPage=1;
        PageHelper.startPage(currPage, pageSize);
        List<TaskOrderPO> pages = taskOrderMapper.selectByStatus(uid,Constant.TASK_UNFINISHED);
        for(TaskOrderPO page:pages){
            TaskVO taskVO = taskController.getTaskById(page.getTask_id());
            page.setUser_id(taskVO.getUser_id());
            page.setUser_name(taskVO.getUser_name());
        }
        PageInfo<TaskOrderPO> pageInfo = new PageInfo<>(pages);
        PageInfo<TaskOrderVO> result = PageInfoUtil.convert(pageInfo, TaskOrderVO.class);
        return result;
    }

    @Override
    public PageInfo<TaskOrderVO> getDone(Integer currPage, Integer pageSize, Integer uid) {
        if(currPage==null || currPage<1) currPage=1;
        PageHelper.startPage(currPage, pageSize);
        List<TaskOrderPO> pages = taskOrderMapper.selectByStatus(uid,Constant.TASK_FINISHED);
        for(TaskOrderPO page:pages){
            TaskVO taskVO = taskController.getTaskById(page.getTask_id());
            page.setUser_id(taskVO.getUser_id());
            page.setUser_name(taskVO.getUser_name());
        }
        PageInfo<TaskOrderPO> pageInfo = new PageInfo<>(pages);
        PageInfo<TaskOrderVO> result = PageInfoUtil.convert(pageInfo, TaskOrderVO.class);
        return result;
    }
}
