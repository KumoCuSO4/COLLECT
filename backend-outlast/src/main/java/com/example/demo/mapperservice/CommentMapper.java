package com.example.demo.mapperservice;
import com.example.demo.po.CommentPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CommentMapper {
    int insert(CommentPO report);

    List<CommentPO> selectByReportId(Integer report_id);

    CommentPO selectByUserId(Integer user_id, Integer report_id);
}
