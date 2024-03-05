package com.example.demo.controller;

import com.example.demo.service.RuleService;
import com.example.demo.vo.ResultVO;
import com.example.demo.vo.RuleVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/rule")
public class RuleController {
    @Resource
    private RuleService ruleService;

    @PostMapping("/update")
    public ResultVO<RuleVO> updateRule(@RequestBody RuleVO rule){
        return ruleService.updateRule(rule);
    }

    @GetMapping("/getRule")
    public RuleVO getRule(){
        return ruleService.getRule();
    }
}
