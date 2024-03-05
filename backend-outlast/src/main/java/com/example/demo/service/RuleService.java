package com.example.demo.service;

import com.example.demo.vo.ResultVO;
import com.example.demo.vo.RuleVO;

public interface RuleService {
    ResultVO<RuleVO> updateRule(RuleVO rule);

    RuleVO getRule();
}
