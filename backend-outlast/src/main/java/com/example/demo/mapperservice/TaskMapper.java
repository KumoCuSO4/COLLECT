package com.example.demo.mapperservice;

import com.example.demo.po.TaskPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface TaskMapper {
    int insert(TaskPO taskPO);

    List<TaskPO> selectAllTask();

    List<TaskPO> selectWithUid(Integer uid);

    List<TaskPO> selectWithUidWithDiffNearest(Integer uid, double diff, Integer limit);

    List<TaskPO> selectCreatedWithUid(Integer uid);

    TaskPO selectById(Integer task_id);

    String selectEquipmentByUid(Integer uid);

    String selectTypeByUid(Integer uid);
}
