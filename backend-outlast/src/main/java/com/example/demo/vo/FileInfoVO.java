package com.example.demo.vo;

import lombok.Data;

@Data
public class FileInfoVO {
    private String uuid;
    private String type;
    private String size;

    public FileInfoVO(String uuid, String type, String size) {
        this.uuid = uuid;
        this.type = type;
        this.size = size;
    }
}
