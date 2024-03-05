package com.example.demo.controller;

import com.example.demo.service.CommentService;
import com.example.demo.util.Constant;
import com.example.demo.vo.CommentVO;
import com.example.demo.vo.ResultVO;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;
    //发布评论
    @PostMapping("/createComment")
    public ResultVO<CommentVO> createComment(@RequestBody CommentVO comment){
        return commentService.createComment(comment);
    }

    //根据报告 report_id以及当前页面号 currpage分页获取全部评论
    @GetMapping("/getComments")
    public PageInfo<CommentVO> getComments(@RequestParam Integer report_id, @RequestParam Integer currPage){
        return commentService.getComments(report_id, Constant.REPORT_PAGE_SIZE,currPage);
    }
}
