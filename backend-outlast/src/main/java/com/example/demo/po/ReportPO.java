package com.example.demo.po;

import java.util.Date;

import com.example.demo.vo.ReportVO;
import lombok.Data;
import lombok.NonNull;

@Data
public class ReportPO {
    private int id;

    private int user_id;

    private int task_id;

    private String description;

    private String step;

    private String equipment;

    private Date post_time;

    private double score;

    public ReportPO(){

    }

    public ReportPO(@NonNull ReportVO report){
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
