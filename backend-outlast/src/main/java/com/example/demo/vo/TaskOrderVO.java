package com.example.demo.vo;

import java.util.Date;

import com.example.demo.po.TaskOrderPO;
import lombok.Data;
import lombok.NonNull;

@Data
public class TaskOrderVO {
    private int id;

    private int user_id;

    private String user_name;

    private int task_id;

    private String task_name;

    private Date create_time;

    private int status;

    public TaskOrderVO(){

    }

    public TaskOrderVO(@NonNull TaskOrderPO taskOrder){
        id = taskOrder.getId();
        user_id = taskOrder.getUser_id();
        user_name = taskOrder.getUser_name();
        task_id = taskOrder.getTask_id();
        task_name = taskOrder.getTask_name();
        create_time = taskOrder.getCreate_time();
        status = taskOrder.getStatus();
    }
}
