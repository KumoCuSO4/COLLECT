package com.example.demo.vo;

import com.example.demo.po.CommentPO;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class CommentVO {
    private int id;

    private int user_id;

    private String user_name;

    private int report_id;

    private int score;

    private String description;

    private Date post_time;

    public CommentVO(){

    }

    public CommentVO(@NonNull CommentPO comment){
        id = comment.getId();
        user_id = comment.getUser_id();
        report_id = comment.getReport_id();
        score = comment.getScore();
        description = comment.getDescription();
        post_time = comment.getPost_time();
    }
}
