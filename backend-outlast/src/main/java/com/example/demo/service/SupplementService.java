package com.example.demo.service;

import com.example.demo.vo.ResultVO;
import com.example.demo.vo.SupplementVO;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletResponse;

public interface SupplementService {
    ResultVO<SupplementVO> createSupplement(SupplementVO supplement);

    PageInfo<SupplementVO> getList(Integer report_id, Integer pageSize, Integer currPage);

    void getSupplementPic(Integer supplementId, String name, HttpServletResponse response);

    SupplementVO getSupplement(Integer supplementId);
}
