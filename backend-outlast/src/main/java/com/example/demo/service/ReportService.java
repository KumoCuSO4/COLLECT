package com.example.demo.service;

import com.example.demo.vo.ReportVO;
import com.example.demo.vo.ResultVO;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ReportService {
    ResultVO<ReportVO> submitReport(ReportVO report);

    PageInfo<ReportVO> getReportByReportTime(Integer taskId,Integer pageSize, Integer currPage);

    ReportVO getReportByReportId(Integer reportId);

    void getReportPic(Integer reportId, String name, HttpServletResponse response);

    ReportVO getSimilarByReportId(Integer reportId);

    Double getSimilarity(Integer reportId);
}
