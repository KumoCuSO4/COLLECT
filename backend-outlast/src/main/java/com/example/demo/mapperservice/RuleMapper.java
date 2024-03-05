package com.example.demo.mapperservice;

import com.example.demo.po.RulePO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RuleMapper {
    int insert(RulePO record);

    int update(RulePO rule);

    RulePO selectById(int id);
}
