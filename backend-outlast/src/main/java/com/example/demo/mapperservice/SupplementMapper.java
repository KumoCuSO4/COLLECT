package com.example.demo.mapperservice;

import com.example.demo.po.ReportPO;
import com.example.demo.po.SupplementPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SupplementMapper {
    int insert(SupplementPO supplement);

    SupplementPO selectByPrimaryKey(Integer id);

    List<SupplementPO> selectByReportId(Integer report_id);

    int selectEquipmentByUid(Integer uid,String equipment);

    int selectPreferenceByUid(Integer uid, String preference);
}
