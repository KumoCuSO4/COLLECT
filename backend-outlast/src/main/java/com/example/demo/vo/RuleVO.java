package com.example.demo.vo;

import com.example.demo.po.RulePO;
import lombok.Data;
import lombok.NonNull;

@Data
public class RuleVO {
    private Integer id;

    private Integer easy_min;

    private Integer easy_max;

    private Integer medium_min;

    private Integer medium_max;

    private Integer hard_min;

    private Integer hard_max;

    private Double easy_increase;

    private Double medium_increase;

    private Double hard_increase;

    public RuleVO() {

    }

    public RuleVO(@NonNull RulePO rulePO){
        this.id = rulePO.getId();
        this.easy_min = rulePO.getEasy_min();
        this.easy_max = rulePO.getEasy_max();
        this.medium_min = rulePO.getMedium_min();
        this.medium_max = rulePO.getMedium_max();
        this.hard_min = rulePO.getHard_min();
        this.hard_max = rulePO.getHard_max();
        this.easy_increase = rulePO.getEasy_increase();
        this.medium_increase = rulePO.getMedium_increase();
        this.hard_increase = rulePO.getHard_increase();
    }
}
