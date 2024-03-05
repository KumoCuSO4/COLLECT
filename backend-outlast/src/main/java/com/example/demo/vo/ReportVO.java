package com.example.demo.vo;

import java.util.Date;

import com.example.demo.po.ReportPO;
import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ReportVO {
    private int id;

    private int user_id;

    private String user_name;

    private int task_id;

    private String description;

    private String step;

    private String equipment;

    private Date post_time;

    private double score;

    private MultipartFile[] pics;

    private String[] picsList;

    public ReportVO(){

    }

    public ReportVO(@NonNull ReportPO report){
        id = report.getId();
        user_id = report.getUser_id();
        task_id = report.getTask_id();
        description = report.getDescription();
        step = report.getStep();
        equipment = report.getEquipment();
        post_time = report.getPost_time();
        score = report.getScore();
    }
}
