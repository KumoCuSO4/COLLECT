package com.example.demo.controller;
import com.example.demo.service.SupplementService;
import com.example.demo.util.Constant;
import com.example.demo.vo.SupplementVO;
import com.example.demo.vo.ResultVO;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping("/supplement")
public class SupplementController {
    @Resource
    private SupplementService supplementService;
    //提交补充说明报告
    @PostMapping("/createSupplement")
    public ResultVO<SupplementVO> createSupplement(SupplementVO supplement){
        return supplementService.createSupplement(supplement);
    }

    //根据任务id获取相关补充说明报告列表
    @GetMapping("/getList")
    public PageInfo<SupplementVO> getList(@RequestParam Integer report_id, @RequestParam Integer currPage){
        return supplementService.getList(report_id, Constant.REPORT_PAGE_SIZE, currPage);
    }

    //根据补充说明id获取相关补充说明报告信息
    @GetMapping("/getSupplement/{supplementId}")
    public SupplementVO getSupplement(@PathVariable Integer supplementId){
        return supplementService.getSupplement(supplementId);
    }

    @GetMapping("/getSupplement/{supplementId}/pic")
    public void getSupplementPic(@PathVariable Integer supplementId, @RequestParam String name, HttpServletResponse response){
        supplementService.getSupplementPic(supplementId,name,response);
    }
}
