package com.example.demo.vo;

import java.util.Date;

import com.example.demo.po.SupplementPO;
import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

@Data

public class SupplementVO {
    private int id;

    private int user_id;

    private String user_name;

    private int report_id;

    private String description;

    private String step;

    private String equipment;

    private Date post_time;

    private MultipartFile[] pics;

    private String[] picsList;

    public SupplementVO(){

    }

    public SupplementVO(@NonNull SupplementPO supplement){
        id = supplement.getId();
        user_id = supplement.getUser_id();
        report_id = supplement.getReport_id();
        description = supplement.getDescription();
        step = supplement.getStep();
        equipment = supplement.getEquipment();
        post_time = supplement.getPost_time();
    }
}
