package com.example.demo.mapperservice;

import com.example.demo.po.ReportPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ReportMapper {
    int insert(ReportPO report);

    ReportPO selectByPrimaryKey(Integer id);

    List<ReportPO> selectByTaskId(Integer task_id);

    List<ReportPO> selectByUid(Integer uid);

    List<ReportPO> selectPreferenceByUid(Integer uid, String preference);

    List<ReportPO> selectEquipmentByUid(Integer uid,String equipment);

    void updateScore(Integer id, double score);
}
