package com.example.demo.controller;

import com.example.demo.mapperservice.UserMapper;
import com.example.demo.service.ReportService;
import com.example.demo.util.Constant;
import com.example.demo.vo.ReportVO;
import com.example.demo.vo.ResultVO;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/report")
public class ReportController {
    @Resource
    private ReportService reportService;
    //提交报告
    @PostMapping("/submit")
    public ResultVO<ReportVO> submitReport(ReportVO report){
        return reportService.submitReport(report);
    }

    //根据任务号 taskId以及当前页面号 currpage分页获取报告列表
    @GetMapping("/getList/{taskId}")
    public PageInfo<ReportVO> getReportByReportTime(@PathVariable Integer taskId, @RequestParam Integer currPage){
        return reportService.getReportByReportTime(taskId, Constant.REPORT_PAGE_SIZE,currPage);
    }

    //根据报告号 reportId 获取当前报告信息
    @GetMapping("/getReport/{reportId}")
    public ReportVO getReportByReportId(@PathVariable Integer reportId){
        return reportService.getReportByReportId(reportId);
    }

    @GetMapping("/getReport/{reportId}/pic")
    public void getReportPic(@PathVariable Integer reportId, @RequestParam String name, HttpServletResponse response){
        reportService.getReportPic(reportId, name, response);
    }

    //在提交报告后根据报告号 reportId 获取相似报告内容
    @GetMapping("/getSimilar")
    public ReportVO getSimilarByReportId(@RequestParam Integer reportId){
        return reportService.getSimilarByReportId(reportId);
    }
}
