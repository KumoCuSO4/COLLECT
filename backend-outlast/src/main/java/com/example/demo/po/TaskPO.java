package com.example.demo.po;

import com.example.demo.vo.TaskVO;
import java.util.Date;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class TaskPO {
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

    private String executable_file;

    private String doc_file;

    private String executable_file_uuid;

    private String doc_file_uuid;

    public TaskPO(){

    }

    public TaskPO(@NonNull TaskVO task, @NotNull String executable_file_uuid, @NotNull String doc_file_uuid){
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
        executable_file = task.getExecutable_file().getOriginalFilename();
        doc_file = task.getDoc_file().getOriginalFilename();
        this.executable_file_uuid = executable_file_uuid;
        this.doc_file_uuid = doc_file_uuid;
    }
}
