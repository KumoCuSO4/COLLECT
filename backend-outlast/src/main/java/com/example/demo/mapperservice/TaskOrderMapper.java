package com.example.demo.mapperservice;

import com.example.demo.po.TaskOrderPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface TaskOrderMapper {
    int insert(TaskOrderPO taskPO);

    List<TaskOrderPO> selectByStatus(Integer uid, Integer status);

    List<TaskOrderPO> selectByUid(Integer uid);

    void updateStatus(Integer tid, Integer status);
}
