package com.example.demo.po;

import com.example.demo.vo.RuleVO;
import lombok.Data;
import lombok.NonNull;

@Data
public class RulePO {
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

    public RulePO() {

    }

    public RulePO(@NonNull RuleVO rule){
        this.id = rule.getId();
        this.easy_min = rule.getEasy_min();
        this.easy_max = rule.getEasy_max();
        this.medium_min = rule.getMedium_min();
        this.medium_max = rule.getMedium_max();
        this.hard_min = rule.getHard_min();
        this.hard_max = rule.getHard_max();
        this.easy_increase = rule.getEasy_increase();
        this.medium_increase = rule.getMedium_increase();
        this.hard_increase = rule.getHard_increase();
    }
}
