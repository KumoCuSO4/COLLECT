package com.example.demo.service;

import com.example.demo.vo.CommentVO;
import com.example.demo.vo.ReportVO;
import com.example.demo.vo.ResultVO;
import com.github.pagehelper.PageInfo;

public interface CommentService {
    ResultVO<CommentVO> createComment(CommentVO report);

    PageInfo<CommentVO> getComments(Integer report_id, Integer pageSize, Integer currPage);
}
