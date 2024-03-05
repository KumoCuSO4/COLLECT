package com.example.demo.po;

import com.example.demo.vo.CommentVO;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class CommentPO {
    private int id;

    private int user_id;

    private int report_id;

    private int score;

    private String description;

    private Date post_time;

    public CommentPO(){

    }

    public CommentPO(@NonNull CommentVO comment){
        id = comment.getId();
        user_id = comment.getUser_id();
        report_id = comment.getReport_id();
        score = comment.getScore();
        description = comment.getDescription();
        post_time = comment.getPost_time();
    }
}
