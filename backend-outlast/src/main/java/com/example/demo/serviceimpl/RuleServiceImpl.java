package com.example.demo.serviceimpl;

import com.example.demo.mapperservice.RuleMapper;
import com.example.demo.service.RuleService;
import com.example.demo.po.RulePO;
import com.example.demo.util.Constant;
import com.example.demo.vo.ResultVO;
import com.example.demo.vo.RuleVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RuleServiceImpl implements RuleService {
    @Resource
    RuleMapper ruleMapper;

    @Override
    public ResultVO<RuleVO> updateRule(RuleVO rule) {
        RulePO rulePO = new RulePO(rule);
        ruleMapper.update(rulePO);
        return new ResultVO<RuleVO>(Constant.REQUEST_SUCCESS,"推荐规则修改成功",new RuleVO(rulePO));
    }

    @Override
    public RuleVO getRule() {
        RulePO rulePO = ruleMapper.selectById(1);
        return new RuleVO(rulePO);
    }
}
