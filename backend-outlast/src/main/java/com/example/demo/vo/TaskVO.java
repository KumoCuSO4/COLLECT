package com.example.demo.vo;

import com.example.demo.po.TaskPO;
import java.util.Date;

import lombok.Data;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TaskVO {
    private int id;

    private String name;

    private String description;

    private Date end_time;

    private int worker_num;

    private int difficulty;

    private String type;

    private String equipment;

    private String others;

    private int user_id;

    private String user_name;

    private MultipartFile executable_file;

    private MultipartFile doc_file;

    public TaskVO(){

    }
    public TaskVO(@NonNull TaskPO task){
        id = task.getId();
        name = task.getName();
        description = task.getDescription();
        end_time = task.getEnd_time();
        worker_num = task.getWorker_num();
        difficulty = task.getDifficulty();
        type = task.getType();
        equipment = task.getEquipment();
        others = task.getOthers();
        user_id = task.getUser_id();
        user_name = task.getUser_name();
    }
}
