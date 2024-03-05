package com.example.demo.po;

import java.util.Date;

import com.example.demo.vo.SupplementVO;
import lombok.Data;
import lombok.NonNull;

@Data

public class SupplementPO {
    private int id;

    private int user_id;

    private int report_id;

    private String description;

    private String step;

    private String equipment;

    private Date post_time;

    public SupplementPO(){

    }

    public SupplementPO(@NonNull SupplementVO supplement){
        id = supplement.getId();
        user_id = supplement.getUser_id();
        report_id = supplement.getReport_id();
        description = supplement.getDescription();
        step = supplement.getStep();
        equipment = supplement.getEquipment();
        post_time = supplement.getPost_time();
    }
}
